package com.qn.computersell.service.impl;

import com.qn.computersell.entity.Address;
import com.qn.computersell.entity.Order;
import com.qn.computersell.entity.OrderItem;
import com.qn.computersell.mapper.OrderMapper;
import com.qn.computersell.service.IAddressService;
import com.qn.computersell.service.ICartService;
import com.qn.computersell.service.IOderService;
import com.qn.computersell.service.ex.InsertException;
import com.qn.computersell.vo.CartVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class OderServiceImpl implements IOderService {
    @Resource
    private OrderMapper orderMapper;

    @Resource
    private IAddressService addressService;

    @Resource
    private ICartService cartService;

    @Override
    public Order createOrder(Integer aid, Integer[] cids, Integer uid, String username) {
        // 创建当前时间对象
        Date NowDate=new Date();
        // 根据cids查询所勾选的购物车列表中的数据
        List<CartVo> list=cartService.getCatVoByUidAndCids(uid,cids);
        // 计算这些商品的总价
        Long totalPrice= Long.valueOf(0);
        for (CartVo cartVo : list) {
            totalPrice+=cartVo.getRealPrice()*cartVo.getNum();
        }
        // 创建订单数据对象
        Order order=new Order();
        // 补全数据：uid
        order.setUid(uid);
        // 查询收货地址数据
        Address address=addressService.getAddressByAidAndUid(aid,uid);
        // 补全数据：收货地址相关的6项
        order.setRecvName(address.getName());
        order.setRecvProvince(address.getProvinceName());
        order.setRecvCity(address.getCityName());
        order.setRecvArea(address.getAreaName());
        order.setRecvPhone(address.getPhone());
        order.setRecvAddress(address.getAddress());
        // 补全数据：totalPrice
        order.setTotalPrice(totalPrice);
        // 补全数据：status
        order.setStatus(0);
        // 补全数据：下单时间
        order.setOrderTime(NowDate);
        order.setCreatedTime(NowDate);
        // 补全数据：日志
        order.setCreatedUser(username);
        order.setModifiedTime(NowDate);
        order.setModifiedUser(username);
        // 插入订单数据
       Integer rows = orderMapper.insertOrder(order);
       if (rows!=1){
           throw new InsertException("插入数据异常");
       }
        // 遍历carts，循环插入订单商品数据
        for (CartVo cartVo : list) {
            // 创建订单商品数据
            OrderItem orderItem=new OrderItem();
            // 补全数据：oid(order.getOid())
            orderItem.setOid(order.getOid());
            // 补全数据：pid, title, image, price, num
            orderItem.setPid(cartVo.getPid());
            orderItem.setTitle(cartVo.getTitle());
            orderItem.setImage(cartVo.getImage());
            orderItem.setPrice(cartVo.getRealPrice());
            orderItem.setNum(cartVo.getNum());
            // 补全数据：4项日志
            orderItem.setCreatedTime(NowDate);
            orderItem.setCreatedUser(username);
            orderItem.setModifiedTime(NowDate);
            orderItem.setModifiedUser(username);
            // 插入订单商品数据
            Integer rows1=orderMapper.insertOrderItem(orderItem);
            if (rows1!=1){
                throw new InsertException("插入订单明细异常");
            }
        }
        // 返回
        return order;
    }
}
