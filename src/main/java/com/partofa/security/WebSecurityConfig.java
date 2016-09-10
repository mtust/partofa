package com.partofa.security;


import com.partofa.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;


@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    protected RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailServiceImpl userDetailService;
    @Autowired
    private MySavedRequestAwareAuthenticationSuccessHandler authenticationSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .authorizeRequests()
                .antMatchers("/public/**").permitAll()
                .antMatchers("/private/**").authenticated()
                .antMatchers("/admin/**").hasAnyAuthority(Role.ROLE_ADMIN.getParamName())
                .and()
                .formLogin()
                .successHandler(authenticationSuccessHandler)
                .failureHandler(new SimpleUrlAuthenticationFailureHandler())
                .and()
                .logout();

    }

    @Autowired
    public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailService)
                .passwordEncoder(passwordEncoder);
    }

    @Bean
    public MySavedRequestAwareAuthenticationSuccessHandler mySuccessHandler() {
        return new MySavedRequestAwareAuthenticationSuccessHandler();
    }

    @Bean
    public SimpleUrlAuthenticationFailureHandler myFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler();
    }

}
