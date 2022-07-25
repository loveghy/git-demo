package com.qn.computersell.mapper;

import com.qn.computersell.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;
    @Test
    public void insertUser(){
        User user=new User();
        User user1=userMapper.findUserByUserName("ghy123");
        if (user1==null){
            user.setUsername("ghy123");
            user.setPassword("123456");
            Integer rows = userMapper.insertUser(user);
            if (rows==1){
                System.out.println("注册成功");
            } else System.out.println("注册失败");
        }
        else if (user1!=null)
            System.out.println("用户已存在");
    }
}
