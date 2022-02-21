package com.test.demo.service;


import com.test.demo.vo.User;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserService {


    //List<User> selectUserList(User user);
    int insertUser(User user) throws Exception;
    User login(User user) throws Exception;
    int updateUser(User user) throws Exception;
    User selectOne(int id) throws Exception;
}
