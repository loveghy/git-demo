package com.qn.computersell.mapper;

import com.qn.computersell.entity.Cart;
import com.qn.computersell.vo.CartVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface CartMapper {

    //插入数据到购物车
    Integer insertCart(Cart cart);

    //更新购物扯商品数量
    Integer updateNumByCid(@Param("cid") Integer cid, @Param("num") Integer num, @Param("modifiedTime") Date modifiedTime, @Param("modifiedUser") String modifiedUser);
    //查询商品是否存在
    Cart findCartByUIdAndPid(@Param("uid") Integer uid,@Param("pid") Integer pid);

    /**
     * 查询某用户的购物车产品信息
     * @param uid
     * @return
     */
    List<CartVo> findCartVoByUid(Integer uid);

    /**
     * 根据cid查询Cart信息
     * @param cid
     * @return
     */
    Cart findCartByCid(Integer cid);

    /**
     *
     * @param cids
     * @return
     */
    List<CartVo> findCartVoByCids(Integer[] cids);
}
