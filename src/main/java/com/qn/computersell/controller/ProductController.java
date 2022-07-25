package com.qn.computersell.controller;

import com.qn.computersell.entity.Product;
import com.qn.computersell.service.IProductService;
import com.qn.computersell.util.JsonResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController extends BaseController{
    @Resource
    private IProductService iProductService;

    @GetMapping("getProduct")
    public JsonResult<List<Product>> getHotProductList(){
        List<Product> data=iProductService.getHotProductList();
        return new JsonResult<>(OK,data);
    }
    @GetMapping("{id}/details")
    public JsonResult<Product> getProductById(@PathVariable("id") Integer id){
        Product product=iProductService.findProductById(id);
        return new JsonResult<>(OK,product);
    }
}
