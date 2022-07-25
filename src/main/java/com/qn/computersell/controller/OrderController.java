package com.qn.computersell.controller;

import com.qn.computersell.entity.Order;
import com.qn.computersell.service.IOderService;
import com.qn.computersell.service.impl.OderServiceImpl;
import com.qn.computersell.util.JsonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/orders")
public class OrderController extends BaseController{

    @Resource
    private IOderService oderService;

    @RequestMapping("create")
    public JsonResult<Order> creatOrder(Integer aid, Integer[] cids , HttpSession session){
        Integer uid=getUidFromSeesion(session);
        String username=getUsername(session);
        Order order = oderService.createOrder(aid,cids,uid,username);
        return new JsonResult<>(OK,order);

    }
}
