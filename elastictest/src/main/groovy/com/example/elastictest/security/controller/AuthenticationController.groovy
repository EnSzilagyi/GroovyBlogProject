package com.example.elastictest.security.controller

import com.example.elastictest.security.JwtTokenUtil
import com.example.elastictest.security.dto.JwtAuthenticationRequest
import com.example.elastictest.security.dto.JwtAuthenticationResponse
import com.example.elastictest.security.dto.JwtError
import com.example.elastictest.security.dto.JwtSignUpRequest
import com.example.elastictest.user.model.User
import com.example.elastictest.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

import javax.validation.Valid

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
class AuthenticationController {
    AuthenticationManager authenticationManager
    UserDetailsService userDetailsService
    JwtTokenUtil jwtTokenUtil
    UserRepository userRepository

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder()

    @Autowired
    AuthenticationController(AuthenticationManager authenticationManager,UserRepository userRepository,
                                    @Qualifier("jwtUserDetailsService") UserDetailsService userDetailsService,
                                     JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager
        this.userRepository = userRepository
        this.userDetailsService = userDetailsService
        this.jwtTokenUtil = jwtTokenUtil
    }


    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    JwtAuthenticationResponse createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest)
            throws DisabledException, BadCredentialsException {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword())
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername())

        return jwtTokenUtil.generateToken(userDetails)
    }

    @GetMapping(value = "/all",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    Iterable<User> findAll() {
        return userRepository.findAll()
    }


    @PostMapping(value = "/signup",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> registerUser(@Valid @RequestBody JwtSignUpRequest signUpRequest) {

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new JwtError(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }
        User user = new User(signUpRequest.getUsername(), signUpRequest.getPassword(), true, new Date());
        System.out.println(user.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(user.getPassword());
       // user.setId("1")
        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new JwtError(true, "User registered successfully"));
    }

    private void authenticate(String username, String password) throws DisabledException, BadCredentialsException {
        Objects.requireNonNull(username)
        Objects.requireNonNull(password)
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password))
    }
}
