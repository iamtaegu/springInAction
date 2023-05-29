package tacos.tacos;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class ActuatorSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http
            //액추에이터 엔드포인트에 적용(EndpointRequest.toAnyEndpoint())
            //일부에만 적용하고 싶으면 EndpointRequest.to() 사용
      .requestMatcher(EndpointRequest.toAnyEndpoint().excluding("health", "info"))
      .authorizeRequests()
        .anyRequest().hasRole("ADMIN")
        
      .and()
    
      .httpBasic();
    // @formatter:on
  }

  /**
   * admin/password
   */
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
      .inMemoryAuthentication()
        .withUser("admin")
        .password("password")
        .authorities("ROLE_ADMIN");
  }
 
  //password no encoding
  @Bean
  public PasswordEncoder passwordEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }
  
}
