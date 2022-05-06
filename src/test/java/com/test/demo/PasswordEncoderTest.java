package com.test.demo;

import com.test.demo.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
public class PasswordEncoderTest {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("패스워드 암호화 테스트")
    void passwordEncoded(){
        //given
        String rawPassword = "12345678";

        //when
        String encodedPassword = passwordEncoder.encode(rawPassword);

        log.info("암호화된 비밀번호={}", encodedPassword);
        // 암호화된 비밀번호=$2a$10$XFdCOsJdUM5IwQzxB9SoSuiOksH0EQ7sow2nt.xg/OirEyzbgxGv2

        //then
        assertAll(
                () -> assertNotEquals(rawPassword, encodedPassword),
                () -> assertTrue(passwordEncoder.matches(rawPassword, encodedPassword))
        );
    }
}

/*
    0505
    - 비밀번호 암호화 처리해서 회원가입 성공
    - BUT 로그인 할때 실제로 내가 입력해서 넘어온 비밀번호 값이랑, DB에 저장되어있는 암호화된 비밀번호랑 matchs()를 이용해 맞는지 틀린지 비교해야 한다.
        if(!passwordEncoder.matches(password, loginUser.getPassword())) {
          System.out.println("비밀번호가 일치하지 않습니다.");
          return false;
       }
    - 먼저 UserId로 User 객체를 뽑아와야 함.
 */
