package com.example.elastictest.security

import com.example.elastictest.security.dto.JwtAuthenticationResponse
import com.example.elastictest.security.dto.JwtUser
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.impl.DefaultClock
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

import java.text.SimpleDateFormat
import java.util.function.Function

@Component
class JwtTokenUtil {
    @Value('secret')
    String secret
    @Value('604800')
    Long expiration
    def clock = DefaultClock.INSTANCE

    JwtTokenUtil(){
        secret = "secret"
        expiration = 604800
    }

    String getUserNameFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token)
        return claims.getSubject()
    }

    Date getIssuedAtDateFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token)
        claims.getIssuedAt()
    }

    Date getExpirationDateFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token)
        claims.getExpiration()
    }

    Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getIssuedAtDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset) && (!isTokenExpired(token) || ignoreTokenExpiration(token))
    }
    private static Boolean ignoreTokenExpiration(String token) {
        return false;
    }


    Boolean validateToken(String token, UserDetails userDetails) {
        JwtUser user = (JwtUser) userDetails
        def pattern = "EEE MMM dd HH:mm:ss zzz yyyy"
        final String username = getUserNameFromToken(token)
        final Date created = getIssuedAtDateFromToken(token)
        final Date expires = new SimpleDateFormat(pattern).parse(user.getLastPasswordResetDate())
        return username == user.getUsername() && !isTokenExpired(token) && !isCreatedBeforeLastPasswordReset(created, expires)
    }


    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromToken(token);
        println claimsResolver.apply(claims)
        return claimsResolver.apply(claims);
    }

    private Boolean isTokenExpired(String token) {
        Date expirationDate = getExpirationDateFromToken(token);
        expirationDate.before(clock.now());
    }

    private static Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private Date calculateExpirationDate(Date createdDate) {
        println new Date(createdDate.getTime() + expiration * 1000);
        return new Date(createdDate.getTime() + expiration * 1000);
    }

    JwtAuthenticationResponse generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private JwtAuthenticationResponse doGenerateToken(Map<String, Object> claims, String subject) {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);

        def token = Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact()
        new JwtAuthenticationResponse(token, expiration)


    }

}
