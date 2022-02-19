package com.test.demo.service;


import com.test.demo.mapper.UserMapper;
import com.test.demo.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired // 아니 이거 해줬는데 왜 에러가 나지?
    private final UserMapper userMapper;

    // UserMapper 인터페이스에서 @Repository 안해주면 컴파일 에러남;;
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public List<User> selectUserList(User user) {
        return userMapper.selectUserList(user);
    }

    @Override
    public void createUser(User user) {
         userMapper.insertUser(user);
    }


}
