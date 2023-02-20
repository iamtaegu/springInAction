package tacos.security;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/design", "/orders")
                    .access("hasRole('ROLE_USER')")
                .antMatchers("/", "/**").access("permitAll")
            .and()
                .httpBasic();
    }

    /**
     * 인메모리 사용자 스토어
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                    .withUser("user1")
                        .password("{noop}pass")// 스프링5부터는 비밀번호 암호화가 필수이고, 예제에서는 {noop}으로 우회
                        .authorities("ROLE_USER")
                .and()
                    .withUser("woody")
                        .password("bullseye")
                        .authorities("ROLE_USER");

    }
}
