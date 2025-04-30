package com.khaled.bank_management_system.config;

import com.khaled.bank_management_system.service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class ConfigurationSecurity {

    private final MyUserDetailsService myUserDetailsService;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(myUserDetailsService);
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());

        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests()
                .requestMatchers("api/v1/employee/delete-employee").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/account/get-all-accounts").hasAuthority("ADMIN")
                .requestMatchers("api/v1/employee/update-employee").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/employee/add-employee").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/employee/get-employees").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/account/active-account").hasAuthority("EMPLOYEE")
                .requestMatchers("/api/v1/account/block-account").hasAuthority("EMPLOYEE")
                .requestMatchers("/api/v1/account/delete-account").hasAuthority("EMPLOYEE")
                .requestMatchers("/api/v1/customer/add-customer").permitAll()
                .requestMatchers("/api/v1/account/add-account").hasAuthority("CUSTOMER")
                .requestMatchers("/api/v1/account/get-my-accounts").hasAuthority("CUSTOMER")
                .requestMatchers("/api/v1/account/transfer").hasAuthority("CUSTOMER")
                .requestMatchers("api/v1/account/deposit").hasAuthority("CUSTOMER")
                .requestMatchers("api/v1/account/view").hasAuthority("CUSTOMER")
                .requestMatchers("/api/v1/account/withdraw").hasAuthority("CUSTOMER")
                .requestMatchers("/api/v1/customer/update-customer").hasAuthority("CUSTOMER")
                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/api/v1/auth/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();

        return httpSecurity.build();
    }
}
