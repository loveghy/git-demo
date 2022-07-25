package com.qn.computersell.mapper;

import com.qn.computersell.entity.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    /**
     * 查找热门产品
     *
     * @return
     */
    List<Product> findHotProductList();

    /**
     * 根据id查询商品
     *
     * @param id
     * @return
     */
    Product findProductById(Integer id);

}
