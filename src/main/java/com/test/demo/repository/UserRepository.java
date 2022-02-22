package com.test.demo.repository;

import com.test.demo.mapper.UserMapper;
import com.test.demo.vo.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// repository와 mapper의 관계는 쌍으로 가거나 다른 매퍼를 연결할수도있다.
@Repository("userRepository")
public class UserRepository {

    private UserMapper userMapper;

    public UserRepository(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    public User checkUser(String userId) throws Exception {
        return userMapper.checkUser(userId);
    }

}
