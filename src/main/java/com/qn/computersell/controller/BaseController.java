package com.qn.computersell.controller;


import com.qn.computersell.controller.ex.*;
import com.qn.computersell.service.ex.*;
import com.qn.computersell.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

public class BaseController {
  public static final Integer OK=200;

  @ExceptionHandler({ServiceException.class,FileUploadException.class})
  public JsonResult<Void> handlerException(Throwable e){
      JsonResult<Void> jsonResult = new JsonResult<Void>();
      if(e instanceof UsernameDuplicateException){
          jsonResult.setCode(4000);
          jsonResult.setMessage("用户名已被占用");
      }else if(e instanceof UserNotFoundException)
      {
          jsonResult.setCode(4001);
          jsonResult.setMessage("用户名不存在");
      }else if(e instanceof PasswordNotMatchException)
      {
          jsonResult.setCode(4002);
          jsonResult.setMessage("密码不正确");
      }
      else if(e instanceof  AddressCountLimitException){
          jsonResult.setCode(4003);
          jsonResult.setMessage("超过最大限度");
      }
      else if (e instanceof AddressNotFoundException){
          jsonResult.setCode(4004);
          jsonResult.setMessage("地址未找到");
      }
      else if (e instanceof AccessDeniedException){
          jsonResult.setCode(4005);
          jsonResult.setMessage("这不是你的地址,拒绝访问");
      }
      else if (e instanceof ProductNotFoundException){
          jsonResult.setCode(4006);
          jsonResult.setMessage("商品未找到");
      }else if (e instanceof CartNotfoundException){
          jsonResult.setCode(4007);
          jsonResult.setMessage("未找到购物车数据");
      }
      else if(e instanceof InsertException){
          jsonResult.setCode(5000);
          jsonResult.setMessage("插入数据时发生未知异常！");
      }else if(e instanceof UpdateException)
      {
          jsonResult.setCode(5001);
          jsonResult.setMessage("更新数据时发生未知异常！");
      }
      else if (e instanceof DeleteException){
          jsonResult.setCode(5002);
          jsonResult.setMessage("删除异常");
      }else if (e instanceof FileEmptyException){
          jsonResult.setCode(6000);
          jsonResult.setMessage("文件空异常");
      }else if (e instanceof FileSizeException){
          jsonResult.setCode(6001);
          jsonResult.setMessage("文件尺寸超过限制");
      }
      else if (e instanceof FileStateException){
          jsonResult.setCode(6002);
          jsonResult.setMessage("文件正在使用");
      }
      else if (e instanceof FileTypeException){
          jsonResult.setCode(6003);
          jsonResult.setMessage("文件类型不反对");
      }
      else if (e instanceof FileUploadIOException){
          jsonResult.setCode(6004);
          jsonResult.setMessage("文件传输异常");
      }
      return jsonResult;
  }
   Integer getUidFromSeesion(HttpSession session)
    {
       return Integer.valueOf(session.getAttribute("uid").toString());

    }
    public String getUsername(HttpSession session)
    {
        return  session.getAttribute("username").toString();

    }
}
