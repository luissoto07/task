package com.tcs.usaa.todotask.security;

import com.tcs.usaa.todotask.filter.CustomAuthenticationFilter;
import com.tcs.usaa.todotask.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void configure(final WebSecurity webSecurity) {
        webSecurity.ignoring().antMatchers(
                "/swagger-ui/**",
                "/swagger-ui.html");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");
        http.authorizeRequests().antMatchers("/swagger-ui/index.html").permitAll();

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);

        http.authorizeRequests().antMatchers("/api/login/**", "/security/token/refresh/**").permitAll();
        http.authorizeRequests().antMatchers("/task/getall/**","/task/get/{id}/**").permitAll();
        http.authorizeRequests().antMatchers("/task/create/**","/task/bystatus/**").hasAnyAuthority("ROLE_MANAGER", "ROLE_ADMIN", "ROLE_SUPER_ADMIN");
        http.authorizeRequests().antMatchers("/task/update/{id}/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN");
        http.authorizeRequests().antMatchers("/task/delete/**").hasAnyAuthority("ROLE_SUPER_ADMIN");

        http.authorizeRequests().antMatchers("/security/users/**").permitAll();
        http.authorizeRequests().antMatchers("/security/user/save/**").hasAnyAuthority("ROLE_MANAGER", "ROLE_ADMIN", "ROLE_SUPER_ADMIN");
        http.authorizeRequests().antMatchers("/security/role/save/**").hasAnyAuthority( "ROLE_ADMIN", "ROLE_SUPER_ADMIN");
        http.authorizeRequests().antMatchers("/security/role/addtouser/**").hasAnyAuthority( "ROLE_ADMIN", "ROLE_SUPER_ADMIN");

         http.authorizeRequests().anyRequest().authenticated();

         http.addFilter(customAuthenticationFilter);
            http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}

