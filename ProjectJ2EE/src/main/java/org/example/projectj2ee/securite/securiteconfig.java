package org.example.projectj2ee.securite;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
public class securiteconfig {
    @Value("${jwt.secretKey}")
    private String secretKey;
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(){
        PasswordEncoder passwordEncoder=passwordEncoder();
        return new InMemoryUserDetailsManager(
                User.withUsername("user1").password(passwordEncoder.encode("12345")).authorities("USER").build(),
                User.withUsername("admin").password(passwordEncoder.encode("12345")).authorities("USER", "ADMIN").build()
        );
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();}

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .sessionManagement(sm->sm.sessionCreationPolicy (SessionCreationPolicy.STATELESS))
                .csrf(csrf->csrf.disable())
                .authorizeHttpRequests (ar->ar.anyRequest().authenticated())
                //.httpBasic(Customizer.withDefaults())
                .oauth2ResourceServer(oa->oa.jwt(Customizer.withDefaults()))
                .build();}
    @Bean
    JwtEncoder jwtEncoder(){
        String secretKey="9fa8372517ac1d389758d3750fc87acf88f542277f26fec1ce4593e93f64e338";
        return new NimbusJwtEncoder(new ImmutableSecret<>(secretKey.getBytes()));
    }
    @Bean
    JwtDecoder jwtDecoder(){
//String secretKey="9foo372517ac1d389758d3758fc67acf88f542277f26fec1ce4593e93f64e338";
        SecretKeySpec secretKeySpec new SecretKeySpec (secretKey.getBytes(), "RSA");
        return NimbusJwtDecoder.withSecretKey(secretKeySpec).macAlgorithm (MacAlgorithm.HS512).build();
    }
    @Bean
    public AuthenticationManager authenticationManager (UserDetailsService userDetailsService){
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder (passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return new ProviderManager(daoAuthenticationProvider);}

    }
