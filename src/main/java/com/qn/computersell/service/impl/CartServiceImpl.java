package com.qn.computersell.service.impl;

import com.qn.computersell.entity.Cart;
import com.qn.computersell.mapper.CartMapper;
import com.qn.computersell.service.ICartService;
import com.qn.computersell.service.IProductService;
import com.qn.computersell.service.ex.AccessDeniedException;
import com.qn.computersell.service.ex.CartNotfoundException;
import com.qn.computersell.service.ex.InsertException;
import com.qn.computersell.service.ex.UpdateException;
import com.qn.computersell.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class CartServiceImpl implements ICartService {
    @Autowired
    private CartMapper cartMapper;

    @Resource
    private IProductService productService;
    /**
     * 实现类实现
     * @param uid 用户
     * @param pid 商品
     * @param amount 数量
     * @param username 用户
     */
    @Override
    public void addToCart(Integer uid, Integer pid, Integer amount, String username) {
        Cart result=cartMapper.findCartByUIdAndPid(uid,pid);
        // 根据参数pid和uid查询购物车中的数据
        // 判断查询结果是否为null
        Date date=new Date();
        if (result==null){
            // 是：表示该用户并未将该商品添加到购物车
            Cart cart=new Cart();
            // -- 创建Cart对象
            cart.setUid(uid);
            cart.setPid(pid);
            cart.setNum(amount);
            cart.setPrice(productService.findProductById(pid).getPrice());
            cart.setCreatedTime(date);
            cart.setCreatedUser(username);
            cart.setModifiedTime(date);
            cart.setModifiedUser(username);
            // -- 封装数据：uid,pid,amount
            // -- 调用productService.findById(pid)查询商品数据，得到商品价格
            // -- 封装数据：price
            Integer rows=cartMapper.insertCart(cart);
            if (rows!=1){
                throw  new InsertException("插入异常");
            }
        }else {
            Integer cid=result.getCid();
            Integer num=result.getNum()+amount;
            Integer rows1=cartMapper.updateNumByCid(cid,num,date,username);
            if (rows1!=1){
                throw new UpdateException("更新购物车数量异常");
            }
        }
        // -- 封装数据：4个日志
        // -- 调用insert(cart)执行将数据插入到数据表中
        // 否：表示该用户的购物车中已有该商品
        // -- 从查询结果中获取购物车数据的id
        // -- 从查询结果中取出原数量，与参数amount相加，得到新的数量
        // -- 执行更新数量

    }

    @Override
    public List<CartVo> getCatVoByUid(Integer uid) {
        return cartMapper.findCartVoByUid(uid);
    }

    @Override
    public Integer addNum(Integer cid, Integer uid, String username) {
        // 调用findByCid(cid)根据参数cid查询购物车数据
        Cart cart=cartMapper.findCartByCid(cid);
        // 判断查询结果是否为null
        if (cart==null){
            throw new CartNotfoundException("未找到");
        }
        // 是：抛出CartNotFoundException

        // 判断查询结果中的uid与参数uid是否不一致
        Integer uid1=cart.getUid();
        if (!uid1.equals(uid)){
            throw new AccessDeniedException("拒绝访问");
        }
        // 是：抛出AccessDeniedException

        // 可选：检查商品的数量是否大于多少(适用于增加数量)或小于多少(适用于减少数量)
        Integer num=cart.getNum()+1;
        Date date=new Date();
        Integer rows=cartMapper.updateNumByCid(cid,num,date,username);
        if (rows!=1){
            throw new UpdateException("更新数量时发生未知异常");
        }
        // 根据查询结果中的原数量增加1得到新的数量num

        // 创建当前时间对象，作为modifiedTime
        // 调用updateNumByCid(cid, num, modifiedUser, modifiedTime)执行修改数量
        return num;
    }

    @Override
    public Integer reduceNum(Integer cid, Integer uid, String username) {
        // 调用findByCid(cid)根据参数cid查询购物车数据
        Cart cart=cartMapper.findCartByCid(cid);
        // 判断查询结果是否为null
        if (cart==null){
            throw new CartNotfoundException("未找到");
        }
        // 是：抛出CartNotFoundException

        // 判断查询结果中的uid与参数uid是否不一致
        Integer uid1=cart.getUid();
        if (!uid1.equals(uid)){
            throw new AccessDeniedException("拒绝访问");
        }
        // 是：抛出AccessDeniedException

        // 可选：检查商品的数量是否大于多少(适用于增加数量)或小于多少(适用于减少数量)
        Integer num=cart.getNum()-1;
        if (num<=1){
            num=1;
        }
        Date date=new Date();
        Integer rows=cartMapper.updateNumByCid(cid,num,date,username);
        if (rows!=1){
            throw new UpdateException("更新数量时发生未知异常");
        }
        // 根据查询结果中的原数量增加1得到新的数量num

        // 创建当前时间对象，作为modifiedTime
        // 调用updateNumByCid(cid, num, modifiedUser, modifiedTime)执行修改数量
        return num;
    }

    @Override
    public List<CartVo> getCatVoByUidAndCids(Integer uid, Integer[] cids) {
        List<CartVo> list=cartMapper.findCartVoByCids(cids);
        Iterator<CartVo> it=list.iterator();
        while (it.hasNext()){
            CartVo cartVo=it.next();
            if (!cartVo.getUid().equals(uid))
            {
                it.remove();
            }
        }
        return list;
    }
}
