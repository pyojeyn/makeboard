package com.test.demo.service;


import com.test.demo.mapper.UserMapper;
import com.test.demo.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired // 아니 이거 해줬는데 왜 에러가 나지?
    private final UserMapper userMapper;

    // 여기서 User도 생성자 주입 해줘야 하나?

    // UserMapper 인터페이스에서 @Repository 안해주면 컴파일 에러남;;
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

//    @Override
//    public List<User> selectUserList(User user) {
//        return userMapper.selectUserList(user);
//    }

    @Override
    public int insertUser(User user) throws Exception {
         return userMapper.insertUser(user);
    }

    @Override
    public User login(User user) throws Exception {
        return userMapper.login(user);
    }

    @Override
    public int updateUser(User user) throws Exception {
        return userMapper.updateUser(user);
    }

    @Override
    public User selectOne(int id) throws Exception {
        return userMapper.selectOne(id);
    }


}
