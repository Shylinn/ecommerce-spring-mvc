package com.nhom3.ecommerceadmin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
        private CustomUserDetailsService userDetailsService;

        @Autowired
        public SecurityConfig(CustomUserDetailsService userDetailsService) {
                this.userDetailsService = userDetailsService;
        }

        @Bean
        public static PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http.csrf(AbstractHttpConfigurer::disable)
                                .authorizeHttpRequests(request -> request
                                                .requestMatchers("/login", "/register/**", "/css/**", "/images/**",
                                                                "/js/**", "/vendor/**", "/")
                                                .permitAll()

                                )
                                .authorizeHttpRequests(request -> request
                                                .requestMatchers("/staff/new", "/staff/add", "/staff/delete",
                                                                "/staffs/**")
                                                .hasAuthority("ADMIN"))
                                .authorizeHttpRequests(request -> request
                                                .anyRequest()
                                                .authenticated())
                                .formLogin(form -> form
                                                .loginPage("/login")
                                                .defaultSuccessUrl("/login-success", true) // always use = true, nếu
                                                                                           // không trang web sẽ lỗi
                                                .loginProcessingUrl("/login")
                                                .failureUrl("/login?error=true")
                                                .permitAll())
                                .logout(
                                                logout -> logout
                                                                .logoutRequestMatcher(
                                                                                new AntPathRequestMatcher("/logout"))
                                                                .logoutSuccessUrl("/login?logoutSuccess=true")
                                                                .permitAll())
                                .exceptionHandling(eh -> eh.accessDeniedPage("/access-deny"));

                return http.build();
        }

        public void configure(AuthenticationManagerBuilder builder) throws Exception {
                builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        }
}
