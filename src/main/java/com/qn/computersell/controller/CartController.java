package com.qn.computersell.controller;

import com.qn.computersell.service.ICartService;
import com.qn.computersell.util.JsonResult;
import com.qn.computersell.vo.CartVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController extends BaseController{

    @Resource
    private ICartService cartService;

    @PostMapping("addToCart")
    public JsonResult<Void> addToCart(Integer pid,Integer amount, HttpSession session){
        Integer uid=getUidFromSeesion(session);
        String username=getUsername(session);
        System.out.println(pid);
        cartService.addToCart(uid,pid,amount,username);

        return new JsonResult<>(OK);
    }

    @GetMapping({"","/"})
    public JsonResult<List<CartVo>> getCartVoList(HttpSession session){
        System.out.println("niha");
        Integer uid=getUidFromSeesion(session);
        List<CartVo> cartVos=cartService.getCatVoByUid(uid);
        return new JsonResult<>(OK,cartVos);
    }

    @RequestMapping("{cid}/num/add")
    public JsonResult<Integer> addNum(@PathVariable("cid") Integer cid,HttpSession session){
        Integer uid=getUidFromSeesion(session);
        String username=getUsername(session);
        Integer num=cartService.addNum(cid,uid,username);
        return new  JsonResult<>(OK,num);
    }

    @RequestMapping("{cid}/num/reduce")
    public JsonResult<Integer> reduceNum(@PathVariable("cid") Integer cid,HttpSession session){
        Integer uid=getUidFromSeesion(session);
        String username=getUsername(session);
        Integer num=cartService.reduceNum(cid,uid,username);
        return new  JsonResult<>(OK,num);
    }

    @RequestMapping("list")
    public  JsonResult<List<CartVo>> getCartVoByCids(HttpSession session,Integer[] cids){
        Integer uid=getUidFromSeesion(session);
        List<CartVo> data=cartService.getCatVoByUidAndCids(uid,cids);
        return new JsonResult<>(OK,data);
    }
}
