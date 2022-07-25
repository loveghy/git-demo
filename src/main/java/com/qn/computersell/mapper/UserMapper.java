package com.qn.computersell.mapper;


import com.qn.computersell.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface UserMapper {
    /**
     * 插入用户数据
     *
     * @param user
     * @return
     */


    Integer insertUser(User user);

    /**
     * 查找指定用户是否存在
     *
     * @param username
     * @return
     */
    User findUserByUserName(String username);

    /**
     * @param uid
     * @param password     新密码
     * @param modifiedUser 修改者
     * @param modifiedTime 修改时间
     */
    Integer updataPasswordByUid(@Param("uid") Integer uid,
                                @Param("password") String password,
                                @Param("modifiedUser") String modifiedUser,
                                @Param("modifiedTime") Date modifiedTime);

    User findUserByUid(Integer uid);

    Integer updateUserInfoByUid(User user);

    Integer updateAvatarByUid(@Param("uid") Integer uid,@Param("avatar") String avatar,
                              @Param("modifiedUser") String modifiedUser,@Param("modifiedTime") Date modifiedTime);

}
