package com.qn.computersell.service.impl;

import com.qn.computersell.entity.Product;
import com.qn.computersell.mapper.ProductMapper;
import com.qn.computersell.service.IProductService;
import com.qn.computersell.service.ex.ProductNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductSericeImpl implements IProductService {
    @Resource
    private ProductMapper productMapper;

    @Override
    public List<Product> getHotProductList() {
        List<Product> list=productMapper.findHotProductList();
        for (Product product : list) {
            product.setPriority(null);
            product.setCreatedUser(null);
            product.setCreatedTime(null);
            product.setModifiedTime(null);
            product.setModifiedUser(null);
        }
        return list;
    }

    @Override
    public Product findProductById(Integer id) {
        Product product=productMapper.findProductById(id);
        if (product==null){
            throw new ProductNotFoundException("商品未找到");
        }
        product.setPriority(null);
        product.setCreatedTime(null);
        product.setCreatedUser(null);
        product.setModifiedUser(null);
        product.setModifiedTime(null);
        return product;
    }

}
