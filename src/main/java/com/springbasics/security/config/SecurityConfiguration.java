package com.springbasics.security.config;


import com.springbasics.security.config.jwt.JWTAuthenticationFilter;
import com.springbasics.security.config.jwt.JWTHelper;
import com.springbasics.security.service.UserService.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration  {
    private final UserService userService;
    private final JWTHelper jwtHelper;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    @Value("#{'${spring.security.PUBLIC_URLs}'.split(',')}")
    String[] PUBLIC_URLs;

    public SecurityConfiguration(UserService userService, JWTHelper jwtHelper, RestAuthenticationEntryPoint restAuthenticationEntryPoint) {
        this.userService = userService;
        this.jwtHelper = jwtHelper;
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
    }

    @Bean
    public DaoAuthenticationProvider getAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf().disable()
            .authorizeHttpRequests()
//            .requestMatchers("/api/v1/auth/*","/").permitAll()
            .requestMatchers(PUBLIC_URLs).permitAll()
            .requestMatchers("/api/v1/admin/**").hasAuthority("ADMIN")
            .anyRequest().authenticated()
            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().exceptionHandling()
            .authenticationEntryPoint(restAuthenticationEntryPoint);
        http.addFilterBefore(new JWTAuthenticationFilter(userService,jwtHelper),UsernamePasswordAuthenticationFilter.class);
        http.authenticationProvider(getAuthenticationProvider());
        http.httpBasic();
        System.out.println(PUBLIC_URLs[0]);
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
