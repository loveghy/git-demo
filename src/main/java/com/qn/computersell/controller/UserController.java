package com.qn.computersell.controller;

import com.qn.computersell.controller.ex.*;
import com.qn.computersell.entity.User;
import com.qn.computersell.service.IUService;
import com.qn.computersell.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
     @Autowired
    private IUService userService;
     @RequestMapping("/register")
     public JsonResult register(User user) {
        // JsonResult<Void> result = new JsonResult<Void>();
         userService.register(user);
         return new JsonResult<>(OK);
      /*   try {
             userService.register(user);
             result.setCode(200);
             result.setMessge("注册成功");

         }catch(UsernameDuplicateException e)
         {
             result.setCode(4000);
             result.setMessge("用户名存在");
         }catch (InsertException e){
             result.setCode(5000);
             result.setMessge("插入数据未知");
         }
         return result;*/

     }
     @RequestMapping("/login")
    public JsonResult<User> login(String username, String password, HttpSession session)
     {
         User data=userService.login(username,password);
         session.setAttribute("uid",data.getUid());
         session.setAttribute("username",data.getUsername());
         return new JsonResult<User>(OK,data);
     }

     @RequestMapping("/changePassword")
      public JsonResult<Void> changePassword(String oldPassword,String newPassword,HttpSession session)
      {
          Integer uid=getUidFromSeesion(session);
          String username=getUsername(session);
          userService.chengePassword(uid,username,oldPassword,newPassword);
          return new JsonResult<Void>(OK);
      }

      @RequestMapping("getUserByUid")
      public JsonResult<User> getUserByUid(HttpSession session){
           User data=userService.getUserByUid(getUidFromSeesion(session));

           return new JsonResult<User>(OK,data);
      }

      @RequestMapping("/changeUserInfo")
      public JsonResult<Void> changeInfo(User user,HttpSession session){
          Integer uid=getUidFromSeesion(session);
          String username=getUsername(session);
          userService.chengeInfo(uid,username,user);
          return new JsonResult<Void>(OK);
      }

    public static final int AVATAR_MAX_SIZE=10*1024*1024;//设置上传文件最大为10M
    public static final List<String> AVATAR_TYPES=new ArrayList<>();
    static{
        AVATAR_TYPES.add("image/jpeg");
        AVATAR_TYPES.add("image/png");
        AVATAR_TYPES.add("image/bmp");
        AVATAR_TYPES.add("image/gif");
    }

    @RequestMapping("upload")
    public JsonResult<String> changeAvatar(@RequestParam("file")MultipartFile file,HttpSession session){
        if (file.isEmpty()){
            throw new FileEmptyException("文件不能为空");
        }

        if (file.getSize()>AVATAR_MAX_SIZE){
            throw new FileSizeException("超过限制尺寸"+(AVATAR_MAX_SIZE/1024)+"kb");
        }

        String contentType=file.getContentType();

        if (!AVATAR_TYPES.contains(contentType)){
            throw new FileTypeException("该类型文件不对");
        }

        String parent=session.getServletContext().getRealPath("upload");
        System.out.println(parent);
        File dir=new File(parent);
        if (!dir.exists()){
            dir.mkdir();
        }
        //获取文件扩展名
        String suffix="";
        String originalFilename = file.getOriginalFilename();
        int beginIndex=originalFilename.lastIndexOf(".");
        if (beginIndex>0){
            suffix=originalFilename.substring(beginIndex);
        }
        String filename= UUID.randomUUID().toString()+suffix;

        File dest=new File(dir,filename);

        try {
            file.transferTo(dest);
        } catch (IllegalStateException e) {
            throw new FileStateException("文件状态异常，文件可能移动或者删除");
        }catch (IOException e){
            throw new FileUploadIOException("文件上传失败，请重新上传");
        }
        //头像路径
        String avatar="/upload/"+filename;

        Integer uid=getUidFromSeesion(session);
        String username=getUsername(session);

        //写入数据库
        userService.changeAvatar(uid,username,avatar);

        //返回头像路径
        return new JsonResult<>(OK,avatar);
    }
}
