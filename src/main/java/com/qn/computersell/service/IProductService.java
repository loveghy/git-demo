package com.qn.computersell.service;

import com.qn.computersell.entity.Product;

import java.util.List;

public interface IProductService {
    List<Product> getHotProductList();

    Product findProductById(Integer id);
}
