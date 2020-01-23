package ru.deft.backend.config;

/*
 * Created by sgolitsyn on 12/16/19
 */

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableOAuth2Sso
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/", "/login**", "/h2-console**")
                .permitAll()
                .antMatchers("/console/**").permitAll()
                .anyRequest()
                .authenticated();
        http.headers().frameOptions().disable();
    }

    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console/**")
                .antMatchers(HttpMethod.POST, "/news/recommend")
                .antMatchers(HttpMethod.PUT, "/news/publish");
    }
}
