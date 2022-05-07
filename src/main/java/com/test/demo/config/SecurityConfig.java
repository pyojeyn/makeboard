package com.test.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


    /*
     * 스프링 시큐리티 규칙
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable()
                .csrf().disable()
                .formLogin().disable()
                .logout().logoutSuccessUrl("/").disable()
                .headers().frameOptions().disable();
    }
}

/*
    0507 로그아웃하고 자동으로 로그인 되는거 해결함.
    역시 시큐리티 쪽이었음.
    로그아웃하고나서 어디로 이동할지도 configure()메소드 안에 설정해줘야함
    .logout().logoutSuccessUrl("/").disable()
 */