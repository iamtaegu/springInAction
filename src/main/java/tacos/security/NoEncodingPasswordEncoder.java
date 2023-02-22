package tacos.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * AuthenticationManagerBuilder.passwordEncoder()는
 * passwordEncoder I/F를 구현한 클래스를 받을 수 있음
 */
@Slf4j
public class NoEncodingPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {
        log.info("[NoEncodingPasswordEncoder.encode]" + charSequence.toString());
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String encodedPwd) {

        System.out.println("[NoEncodingPasswordEncoder.matches]" + charSequence.toString() + " : " + encodedPwd);
        log.info("[NoEncodingPasswordEncoder.matches]" + charSequence.toString() + " : " + encodedPwd);
        return charSequence.toString().equals(encodedPwd);
    }

}
