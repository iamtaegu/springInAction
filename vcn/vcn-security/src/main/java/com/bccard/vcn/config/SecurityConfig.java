package com.bccard.vcn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http) {

        return http
                .authorizeExchange()
                   .pathMatchers("/security","/WebSecurity").hasAuthority("ADMIN")
                   .pathMatchers("/h2-console").hasAnyAuthority("USER", "ADMIN")
                   .anyExchange().permitAll() //anyExchange가
                .and()
                    .httpBasic()
                .and()
                    .formLogin()
                .and()
                  .csrf().disable() // disable csrf protection
                  .headers().frameOptions().disable() // disable X-Frame-Options header
                .and()
                .build();
    }
    /*@Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .authorizeExchange()
                .pathMatchers("/api/public/**").permitAll()
                .anyExchange().authenticated()
                .and()
                .httpBasic()
                .and()
                .formLogin()
                .and()
                .build();
    } */

    /**
     * MapReactiveUserDetailsService는 ReactiveUserDetailsService I/F의 간단한 구현체ㅇ
     */
    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user").password("userpassword").roles("USER").build();
        UserDetails admin = User.withUsername("admin").password("adminpassword").roles("ADMIN").build();
        return new MapReactiveUserDetailsService(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new NoEncodingPasswordEncoder(); // bcrypt를 해싱 암호화한다

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
