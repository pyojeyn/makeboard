package com.test.demo.mapper;


import com.test.demo.vo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


// DAO ?
//@Repository
@Mapper  // 이거 안하면 UserMapper Class 지정받아서 SqlSession? 이거 주입받고 SelectOne이나 SelectList 사용해야 하나?
public interface UserMapper {
     List<User> selectUserList() throws Exception;

     int insertUser(User user) throws Exception;


     User login(User user) throws Exception;

     User checkLogin(User user) throws Exception; // 로그인 시 비밀번호 아이디 확인

     User selectOne(int id) throws Exception;

     int updateUser(User user) throws Exception;

     int deleteUser(int id) throws Exception;

     User checkUser(String userId) throws Exception;

     int checkId(String userId) throws Exception; // 아이디 중복 체크

     User getPw(String userId) throws Exception; // 암호화된 비번 꺼내오기

     int changePassword(int id, String encodedNewPw) throws Exception;

     User forCheckPw(int Id) throws Exception;

}
