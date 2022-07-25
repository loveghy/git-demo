package com.qn.computersell.service;

import com.qn.computersell.vo.CartVo;

import java.util.List;

public interface ICartService {

    /**
     * 添加购物车
     * @param uid 用户
     * @param pid 商品
     * @param amount 数量
     * @param username 用户
     */
    void addToCart(Integer uid,Integer pid,Integer amount,String username);

    /**
     *
     * @param uid
     * @return
     */
    List<CartVo> getCatVoByUid(Integer uid);

    Integer addNum(Integer cid,Integer uid,String username);

    Integer reduceNum(Integer cid, Integer uid, String username);

    List<CartVo> getCatVoByUidAndCids(Integer uid,Integer[] cids);
}
