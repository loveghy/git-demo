package com.qn.computersell.service;

import com.qn.computersell.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {
    @Autowired
    private IUService iuService;

    @Test
    public void register(){
        try {
            User user=new User();
            user.setUsername("zisf1");
            user.setPassword("123");
            user.setEmail("12qq.com");
            user.setPhone("123456");
            user.setGender(1);
            user.setAvatar("D://adas");
            iuService.register(user);
            System.out.println("注册成功");
        }catch (Exception e){
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }

    }
}
