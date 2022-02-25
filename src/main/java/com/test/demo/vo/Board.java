package com.test.demo.vo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Board {
    private int id;
    private String writer;
    private String title;
    private String content;
    private LocalDate boardRegdate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getBoardRegdate() {
        return boardRegdate;
    }

    public void setBoardRegdate(LocalDate boardRegdate) {
        this.boardRegdate = boardRegdate;
    }

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", boardRegdate=" + boardRegdate +
                '}';
    }
}
