package com.qn.computersell.controller;

import com.qn.computersell.entity.UserEntity;
import com.qn.computersell.mapper.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/userEntity")
public class UserEntityController{
        @Resource
        UserDao userDao;

        @PostMapping("/addUser")
        public String addUser(String username, String password, Integer age) {
            UserEntity user = new UserEntity();
            user.setUsername("ghy111");
            user.setPassword("1234");
            user.setAge(23);
            user.setId(1);

           /* user.setUsername(username);
            user.setPassword(password);
            user.setAge(age);*/
            return String.valueOf(userDao.save(user).getId());// 返回id做验证
        }

        @DeleteMapping("/deleteUser")
        public String deleteUser(Integer id) {
            userDao.deleteById(id);
            return "Success!";
        }

        @PutMapping("/updateUser")
        public String updateUser(Integer id, String username, String password, Integer age) {
            UserEntity user = new UserEntity();
            user.setId(id);
            user.setUsername(username);
            user.setPassword(password);
            user.setAge(age);
            return String.valueOf(userDao.save(user).getId());// 返回id做验证
        }

        @GetMapping("/getUser")
        public UserEntity getUser(Integer id) {
            return userDao.findById(id).get();
        }

        @GetMapping("/getAllUsers")
        public Iterable<UserEntity> getAllUsers() {
            return userDao.findAll();
        }
    }

