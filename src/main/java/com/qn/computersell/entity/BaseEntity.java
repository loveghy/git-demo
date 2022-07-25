package com.qn.computersell.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public abstract class BaseEntity implements Serializable {
    private String createdUser;
    private Date createdTime;
    private String modifiedUser;
    private Date modifiedTime;
}
