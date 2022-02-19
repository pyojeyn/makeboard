package com.test.demo.mapper;


import com.test.demo.vo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


// DAO ?
//@Repository
@Mapper  // 이거 안하면 UserMapper Class 지정받아서 SqlSession? 이거 주입받고 SelectOne이나 SelectList 사용해야 하나?
public interface UserMapper {
     List<User> selectUserList(User user);
     void insertUser(User user);
}
