package com.qn.computersell.service;

import com.qn.computersell.entity.Order;

public interface IOderService {
    Order createOrder(Integer aid,Integer[] cids,Integer uid,String username);
}
