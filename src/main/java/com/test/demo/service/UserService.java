package com.test.demo.service;


import com.test.demo.vo.User;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserService {

    List<User> selectUserList() throws Exception;
    int insertUser(User user) throws Exception;
    User login(User user) throws Exception;
    int updateUser(User user) throws Exception;
    User checkUser(String userId) throws Exception;
    User selectOne(int id) throws Exception;
    int deleteUser(int id) throws Exception;
    User checkLogin(User user) throws Exception;
    int checkId(String userId) throws Exception;
    User getPw(String userId) throws Exception;
    int changePassword(int id, String encodedNewPw) throws Exception;
    User forCheckPw(int Id) throws Exception;

}
