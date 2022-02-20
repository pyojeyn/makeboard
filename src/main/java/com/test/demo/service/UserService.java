package com.test.demo.service;


import com.test.demo.vo.User;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserService {
    List<User> selectUserList(User user);
    void insertUser(User user);
}
