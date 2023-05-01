package com.bccard.vcn.config;

import org.springframework.security.crypto.password.PasswordEncoder;

public class NoEncodingPasswordEncoder implements PasswordEncoder {
    //입력된 패스워드를 암호하하지 않고 반환
    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    //encode에서 반환된 비밀번호를 db에 사용자 비번과 비교
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return rawPassword.toString().equals(encodedPassword);
    }
}
