package com.qn.computersell.service;

import com.qn.computersell.entity.User;

public interface IUService {
    /*用户注册*/
    void register(User user);


    User login(String username, String password);

    void chengePassword(Integer uid, String username, String oldPassword, String newPassword);

    User getUserByUid(Integer uidFromSeesion);

    void chengeInfo(Integer uid, String username, User user);

    void changeAvatar(Integer uid,String username,String avatar);
}
