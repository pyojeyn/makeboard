package com.test.demo.vo;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    //private int id;
    private String userId;
    private String userPw;
    private String userNkname;
    private LocalDate userRegdate;

}
