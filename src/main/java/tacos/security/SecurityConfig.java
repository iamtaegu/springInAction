package tacos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * UserDetailsService 인스턴스를 주입해주고
     * JDBC에서 했던 것처럼 비밀번호 인코더를 구성
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                /**
                 * encoder()에 @Bean 애노테이션이 지정되어 있기 때문에
                 * encoder()에서 생성한 BCryptPasswordEncoder 인스턴스가 스프링 애플리케이션 컨텍스트에 등록, 관리, 그리고 (필요시) 주입 됨
                 * 이를 통해 우리가 원하는 종류의 passwordEncoder 객체를 스프링의 관리하에 사용할 수 있음
                 *  단, @Component 애노테이션과 의미를 다름
                 */
                .passwordEncoder(encoder());
    }

    /**
     *
    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/design", "/orders")
                    .access("hasRole('ROLE_USER')")
                .antMatchers("/h2-console/**").access("permitAll") // h2-console 추가
                .antMatchers("/", "/**").access("permitAll")
            .and()
                .csrf() // h2-console 추가
                .ignoringAntMatchers("/h2-console/**").disable() // h2-console 추가
                .httpBasic();
        http.headers().frameOptions().disable(); //h2-console
    }


     * JDBC 사용자 스토어
     * 스프링5부터는 패스워드 암호화가 필수
     * PasswordEncoder I/F를 구현하고 암복호화 하지 않는 인스턴스 생성
     * @param auth
     * @throws Exception

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery( //사용자 쿼리
                        "select username, password, enabled from Users " +
                                "where username=?")
                .authoritiesByUsernameQuery( //권한 쿼리
                        "select username, authority from Authorities " +
                                "where username=?")
                .passwordEncoder(new NoEncodingPasswordEncoder());
    }
    */

    /**
     * 인메모리 사용자 스토어
     * @param auth
     * @throws Exception
     * ctrl + o
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

    }*/
}
