package com.bccard.vcn.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
//@EnableWebSecurity // 스프링 MVC
@EnableWebFluxSecurity
public class SecurityConfig {

    /**
     * 스프링 MVC 보안 설정과 달리
     * 상속을 받지 않고 SecurityWebFilterChain 빈으로 선언
     * build() 메서드를 호출하여 보안 규칙을 SecurityWebFilterChain에 조립하고 반환
     * ServerHttpSecurity는 스프링 시큐리티 5에 추가되었고, HttpSecurity의 리액티브 버전
     */
    /*@Bean
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http) {

        return http
                .authorizeExchange()
                .pathMatchers("/security","/WebSecurity").hasAuthority("ADMIN")
                .anyExchange().permitAll() //anyExchange가 /** 반환함
                .and()
                .build();
    } */
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange()
                  .pathMatchers("/h2-console/**").permitAll() // allow access to h2-console
                  .anyExchange().permitAll() // allow all other requests without authentication
                  .and()
                .csrf()
                 .disable() // disable csrf protection
                .headers()
                 .frameOptions()
                      .disable(); // disable X-Frame-Options header
        return http.build();
    }

// extends WebSecurityConfigurerAdapter { // 스프링 MVC

/*    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/security","/WebSecurity").hasAuthority("ADMIN")
                .antMatchers("/**").permitAll();
    } */

}
