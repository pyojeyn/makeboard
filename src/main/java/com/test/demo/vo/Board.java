package com.test.demo.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class Board  extends Common{
    private int id;
    private String writer;
    private String title;
    private String content;
    private LocalDate boardRegdate;
}
