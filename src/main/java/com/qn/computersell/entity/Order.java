package com.qn.computersell.entity;

import lombok.Data;

import java.util.Date;

/**
 * 订单实体
 */
@Data
public class Order extends BaseEntity{
    private Integer oid;
    private Integer uid;
    private String recvName;
    private String recvPhone;
    private String recvProvince;
    private String recvCity;
    private String recvArea;
    private String recvAddress;
    private Long totalPrice;
    private Integer status;
    private Date orderTime;
    private Date payTime;
}
