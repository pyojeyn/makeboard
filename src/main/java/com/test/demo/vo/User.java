package com.test.demo.vo;

// import lombok.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class User {
    private int id;
    private String userId;
    private String userPw;
    private String userNkname;
    private LocalDate userRegdate;
}

/*
<< 롬복 lombok @Getter @Setter >>
* 설정 방법
  1. build.gradle 에다 ↓
     configurations {
        compileOnly {
            extendsFrom annotationProcessor
        }
    }
    dependencies 안에다 ↓
    //lombok 라이브러리 추가 시작
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'

    testCompileOnly 'org.projectlombok:lombok'
    testAnnotationProcessor 'org.projectlombok:lombok'
    //lombok 라이브러리 추가 끝

    2. Preferences -> Plugin -> lombok 검색 (이미 된경우는 안나와있어서 install 항목 클릭해서 확인)
    3. 중요 : Preferences -> Annotation Processors 검색 -> Enable annotation processing 체크 (재시작) 이거 무조건 해줘야됨!!!!!

    처음에 할때는 죽어도 안되더니.. 인강 보고 깨달았다.. annotationProcessor 관련해서 추가적인 진행(작업) 을 안해줘서 그런 거 같다.
*/