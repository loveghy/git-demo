package com.qn.computersell.entity;

import lombok.Data;

@Data
public class Product extends BaseEntity{
    private Integer id;//商品id
    private Integer categoryId;//分类Id
    private String itemType;//商品系列
    private String title;//标题
    private String sellPoint;//卖点
    private Long price;//价格
    private Integer num;//库存
    private String image;//图片
    private Integer status;//状态
    private Integer priority;//显示优先级

}
