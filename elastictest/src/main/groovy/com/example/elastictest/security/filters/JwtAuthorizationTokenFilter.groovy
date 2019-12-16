package com.example.elastictest.security.filters

import com.example.elastictest.security.JwtTokenUtil
import com.example.elastictest.security.service.JwtUserDetailsService
import io.jsonwebtoken.ExpiredJwtException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthorizationTokenFilter extends OncePerRequestFilter {
    UserDetailsService userDetailsService;
    JwtTokenUtil jwtTokenUtil;
    String tokenHeader;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    JwtAuthorizationTokenFilter(JwtUserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil, @Value('${jwt.header}') String tokenHeader) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.tokenHeader = tokenHeader;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestHeader = request.getHeader(this.tokenHeader);
        String authToken = null;
        String username = null;
        ;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring(7);
            try {
                username = jwtTokenUtil.getUserNameFromToken(authToken);
            } catch (IllegalArgumentException ex) {
                logger.error("An error has occurred during getting the username from the token");
            } catch (ExpiredJwtException e){
                logger.error("The Token is expired and not valid anymore" + e.getMessage())
            }
        } else {
            logger.info(requestHeader);
            logger.info("Couldn't find bearer string in the header, so we will ignore it");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                logger.info(new StringBuilder("User ").append(username).append(" detected").toString());
            } else {
                logger.warn("Invalid token for user" + username);
            }
            Object principal = SecurityContextHolder.getContext().getAuthentication().getCredentials();
            println principal
        }
        filterChain.doFilter(request, response);
    }
}
