package com.qn.computersell.service.impl;


import com.qn.computersell.entity.User;
import com.qn.computersell.mapper.UserMapper;
import com.qn.computersell.service.IUService;
import com.qn.computersell.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;


import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void register(User user) {
        String username=user.getUsername();
        User result=userMapper.findUserByUserName(username);
        if(result!=null)
        {
            throw new UsernameDuplicateException("用户名已被占用");
        }
        // 创建当前时间对象
        Date date =new Date();
        // 补全数据：加密后的密码
        //获取盐值
        String salt= UUID.randomUUID().toString().toUpperCase();
        user.setPassword(getMd5Password(salt,user.getPassword()));
        // 补全数据：盐值
        user.setSalt(salt);
        // 补全数据：isDelete(0)
        user.setIsDelete(0);
        // 补全数据：4项日志属性
        user.setCreatedUser(username);
        user.setCreatedTime(date);
        user.setModifiedUser(username);
        user.setModifiedTime(date);
        Integer rows=userMapper.insertUser(user);
        if(rows!=1)
        {
            throw new InsertException("注册时发生未知异常");
        }
    }

    /**
     *md5进行3次加密
     */
    private String getMd5Password(String salt,String password){
        for(int i=1;i<=3;i++)
        {
            password= DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;
    }


    @Override
    public User login(String username, String password) {
        User result=userMapper.findUserByUserName(username);
        if(result==null)
        {
            throw new UserNotFoundException("用户未找到");
        }
        String oldPassword=result.getPassword();
        String newPassword=getMd5Password(result.getSalt(),password);
        if(!oldPassword.equals(newPassword))
        {
            throw new PasswordNotMatchException("密码不正确");
        }
        if(result.getIsDelete()==1)
        {
            throw new UserNotFoundException("用户未找到");
        }
        User user=new User();
        user.setUid(result.getUid());
        user.setUsername(username);
        user.setAvatar(result.getAvatar());

        return user;
    }

    @Override
    public void chengePassword(Integer uid, String username, String oldPassword, String newPassword) {
         User result=userMapper.findUserByUid(uid);
         if(result==null){
             throw new UserNotFoundException("用户不存在");
         }
         if(result.getIsDelete()==1){
             throw new UserNotFoundException("用户不存在");
         }
         //从查询结果中取出盐值
         String salt=result.getSalt();
         String oldMd5password=getMd5Password(salt,oldPassword);
         if(!result.getPassword().equals(oldMd5password)){
             throw new PasswordNotMatchException("原密码不正确");
         }

         String newMd5Password=getMd5Password(salt,newPassword);
         Date date=new Date();
         Integer rows=userMapper.updataPasswordByUid(result.getUid(),newMd5Password,username,date);
         if(rows!=1){
             throw new UpdateException("更新密码发生未知错误");
         }
    }

    @Override
    public User getUserByUid(Integer uid) {
        // 调用userMapper的findByUid()方法，根据参数uid查询用户数据
        User result=userMapper.findUserByUid(uid);
        // 判断查询结果是否为null
        // 是：抛出UserNotFoundException异常
        if(result==null)
        {
            throw new UserNotFoundException("用户不存在");
        }
        // 判断查询结果中的isDelete是否为1
        // 是：抛出UserNotFoundException异常
         if(result.getIsDelete()==1)
         {
             throw new UserNotFoundException("用户不存在");
         }

        // 创建新的User对象
        User user=new User();
        // 将以上查询结果中的username/phone/email/gender封装到新User对象中
         user.setUsername(result.getUsername());
         user.setPhone(result.getPhone());
         user.setEmail(result.getEmail());
         user.setGender(result.getGender());
        // 返回新的User对象
        return user;
    }

    @Override
    public void chengeInfo(Integer uid, String username, User user) {
        // 调用userMapper的findByUid()方法，根据参数uid查询用户数据
        User result=userMapper.findUserByUid(uid);
        // 判断查询结果是否为null
        // 是：抛出UserNotFoundException异常
        if(result==null)
        {
            throw new UserNotFoundException("用户不存在");
        }
        // 判断查询结果中的isDelete是否为1
        // 是：抛出UserNotFoundException异常
        if(result.getIsDelete()==1)
        {
            throw new UserNotFoundException("用户不存在");
        }
        // 向参数user中补全数据：uid
        user.setUid(uid);
        // 向参数user中补全数据：modifiedUser(username)
        user.setModifiedUser(username);
        // 向参数user中补全数据：modifiedTime(new Date())
        user.setModifiedTime(new Date());
        // 调用userMapper的updateInfoByUid(User user)方法执行修改，并获取返回值
        Integer rows=userMapper.updateUserInfoByUid(user);
        // 判断以上返回的受影响行数是否不为1
        // 是：抛出UpdateException异常
        if(rows!=1){
            throw new UpdateException("更新数据发生未知异常");
        }

    }

    @Override
    public void changeAvatar(Integer uid, String username, String avatar) {
        // 调用userMapper的findByUid()方法，根据参数uid查询用户数据
        User result = userMapper.findUserByUid(uid);
        // 检查查询结果是否为null
        if (result == null) {
            // 是：抛出UserNotFoundException
            throw new UserNotFoundException("用户数据不存在");
        }

        // 检查查询结果中的isDelete是否为1
        if (result.getIsDelete().equals(1)) {
            // 是：抛出UserNotFoundException
            throw new UserNotFoundException("用户数据不存在");
        }

        // 创建当前时间对象
        Date now = new Date();
        // 调用userMapper的updateAvatarByUid()方法执行更新，并获取返回值
        Integer rows = userMapper.updateAvatarByUid(uid, avatar, username, now);
        // 判断以上返回的受影响行数是否不为1
        if (rows != 1) {
            // 是：抛出UpdateException
            throw new UpdateException("更新用户数据时出现未知错误，请联系系统管理员");
        }
    }
}
