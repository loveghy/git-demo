package com.qn.computersell.service;

import com.qn.computersell.entity.Address;

import java.util.List;

public interface IAddressService {
    /**
     *
     * @param uid
     * @param username
     * @param address
     */
    void addNewAddress(Integer uid, String username, Address address);

    List<Address> getAddressByUid(Integer uid);

    /**
     *
     * @param aid
     * @param uid
     * @param username
     */
    void setDefault(Integer aid,Integer uid,String username);

    /**
     *
     * @param aid
     * @param uid
     * @param username
     */
    void deleteByAid(Integer aid,Integer uid,String username);

    Address getAddressByAidAndUid(Integer aid,Integer uid);
}
