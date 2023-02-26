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
     * 웹 수준에서 보안 처리
     *  1. HTTP 요청 처리를 허용하기 전에 충족되어야 할 특정 보안 조건을 구성
     *  2. 커스텀 로그인 페이지 구성
     *  3. 사용자가 애플리케이션을 로그아웃 할 수 있도록 함
     *  4. CSRF 공격으로부터 보호
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * authorizeRequests()는 ExpressionInterceptUrlRegistry 객체를 반환
         * 이 객체는 URL 경로와 패턴 및 해당 경로의 보안 요구사항을 구성할 수 있음
         *  ㅁ 여기서는 두 가지 보안 규칙을 지정
         *      - /design, /orders는 ROLE_USER 권한을 갖는 사용자에게만 허용
         *      - 이외의 모든 요청은 모든 사용자에게 허용
         */
        http
        .authorizeRequests()
            .antMatchers("/design", "/orders")
                //.hasRole("ROLE_USER") // 보안 메서드
                // 아래는 스프링 표현식으로 hasRole을 표현
                .access("hasRole('ROLE_USER')") //SpEL
            .antMatchers("/", "/**").permitAll()

            //로그인
            .and()
                .formLogin()
                // WebConfig 뷰 컨트롤러 등록
                .loginPage("/login")
                .defaultSuccessUrl("/design")
                // 스프링 시큐리티 인증 default url은 login
                //.loginProcessingUrl("/authenticate")
                // username, password가 default
                //.usernameParameter("user")
                //.passwordParameter("pwd");

            //로그아웃
            .and()
                .logout()
                .logoutSuccessUrl("/")

            // CSRF 공격 방어
            // 폼의 hidden 필드에 name:_csrf으로 csrf 토큰 생성
            // 제출된 폼의 csrf 토큰 값과 서버가 갖고 있는 값을 비요하고
            // 그 값이 다르면 허용되지 않는 form에서의 제출로 간주
            .and()
                .csrf();
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
