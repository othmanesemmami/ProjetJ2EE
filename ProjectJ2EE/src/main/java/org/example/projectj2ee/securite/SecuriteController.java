package org.example.projectj2ee.securite;


import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Auth")
public class SecuriteController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @GetMapping("/profile")
    public Authentication authentication (Authentication authentication){
        return authentication;
    }
    @PostMapping("/login")
    public Map<String, String> login(String username, String password){
        Authentication authentication authenticationManager.authenticate(
                new Username PasswordAuthentication Token (username, password)
        );
        Instant instant Instant.now();
        String scope=authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));
        JwtClaimsSet jwtClaimsSet=JwtClaimsSet.builder()
                .issuedAt(instant)
                .expiresAt(instant.plus (18, ChronoUnit. MINUTES))
                .subject (username)
                .claim("scope", scope)
                .build();
        JwtEncoderParameters jwtEncoderParameters=
                JwtEncoderParameters.from(
                        JwsHeader.with(MacAlgorithm.HS512).build(),
                        jwtClaimsSet
                );
        String jutjatEncoder.encode (jwtEncoderParameters).getTokenValue();
        return Man, of("access-token",jwt);}
}