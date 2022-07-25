package com.qn.computersell.controller;

import com.qn.computersell.entity.Address;
import com.qn.computersell.service.IAddressService;
import com.qn.computersell.util.JsonResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController extends BaseController{
    @Resource
    private IAddressService addressService;

    @PostMapping("/addNewAddress")
    public JsonResult<Void> addnewAddress(Address address, HttpSession session){
        Integer uid=getUidFromSeesion(session);
        String username=getUsername(session);
        System.out.println(address.toString());
        addressService.addNewAddress(uid,username,address);

        return new JsonResult<>(OK);
    }

    @GetMapping({"","/"})
    public JsonResult<List<Address>> getAddressByUid(HttpSession session){
        Integer uid=getUidFromSeesion(session);
        List<Address> list=addressService.getAddressByUid(uid);
        return new JsonResult<>(OK,list);
    }

     @PostMapping("{aid}/setDefault")
    public JsonResult<Void> setDefault(@PathVariable("aid") Integer aid,HttpSession session){
        Integer uid=getUidFromSeesion(session);
        String username=getUsername(session);
         System.out.println(aid);
         addressService.setDefault(aid,uid,username);
        return new JsonResult<>(OK);
    }

    @PostMapping("{aid}/delAddress")
    public JsonResult<Void> deleteAddress(@PathVariable("aid") Integer aid,HttpSession session){
        Integer uid=getUidFromSeesion(session);
        String username=getUsername(session);
        System.out.println(aid);
        addressService.deleteByAid(aid,uid,username);
        return new JsonResult<>(OK);
    }
}
