package com.qn.computersell.entity;


import lombok.Data;

@Data
public class Address extends BaseEntity{
     private Integer aid;
     private Integer uid;
     private String name;
     private String provinceName;
     private String provinceCode;
     private String cityName;
     private String cityCode;
     private String areaName;
     private String areaCode;
     private String zip;
     private String address;
     private String phone;
     private String tel;
     private String tag;//地址类型
     private  Integer isDefault;

}
