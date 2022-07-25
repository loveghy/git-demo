package com.qn.computersell.service.impl;

import com.qn.computersell.entity.Address;
import com.qn.computersell.mapper.AddressMapper;
import com.qn.computersell.service.IAddressService;
import com.qn.computersell.service.IDistrictService;
import com.qn.computersell.service.ex.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class AddressService implements IAddressService {
    @Resource
    private AddressMapper addressMapper;

    @Resource
    private IDistrictService districtService;

    @Value("${user.address.max-count}")
    private int maxCount;

    @Override
    public void addNewAddress(Integer uid, String username, Address address) {
        Integer count = addressMapper.countAddressesByUid(uid);
        if (count > maxCount) {
            throw new AddressCountLimitException("用户地址数超过最大限度");
        }

        if (address.getZip().length() > 6) {
            throw new AddressCountLimitException("邮编超过最大长度");
        }
        String provinceName = districtService.getNameByCode(address.getProvinceCode());
        String cityName = districtService.getNameByCode(address.getCityCode());
        String areaName = districtService.getNameByCode(address.getAreaCode());
        address.setProvinceName(provinceName);
        address.setCityName(cityName);
        address.setAreaName(areaName);
        address.setUid(uid);
        if (count == 0) {
            address.setIsDefault(1);
        } else {
            address.setIsDefault(0);
        }
        Date date = new Date();
        address.setCreatedTime(date);
        address.setCreatedUser(username);
        address.setModifiedTime(date);
        address.setModifiedUser(username);

        Integer rows = addressMapper.insertAddress(address);

        if (rows != 1) {
            throw new InsertException("插入地址发生未知异常");
        }
    }

    @Override
    public List<Address> getAddressByUid(Integer uid) {
        List<Address> list = addressMapper.findAddressByUid(uid);
        for (Address address : list) {
            address.setProvinceCode(null);
            address.setCityCode(null);
            address.setUid(null);
            address.setCreatedUser(null);
            address.setCreatedTime(null);
            address.setModifiedUser(null);
            address.setModifiedTime(null);
        }
        return list;
    }

    @Override
    public void setDefault(Integer aid, Integer uid, String username) {
        // 根据参数aid，调用addressMapper中的findByAid()查询收货地址数据
        Address address = addressMapper.findAddressByAid(aid);

        // 判断查询结果是否为null
        // 是：抛出AddressNotFoundException
        if (address == null) {
            throw new AddressNotFoundException("地址未找到");
        }
        // 判断查询结果中的uid与参数uid是否不一致(使用equals()判断)
        // 是：抛出AccessDeniedException：非法访问
        if (!address.getUid().equals(uid)) {
            throw new AccessDeniedException("拒绝访问，不是你的地址");
        }
        // 调用addressMapepr的updateNonDefaultByUid()将该用户的所有收货地址全部设置为非默认，并获取返回的受影响的行数
        // 判断受影响的行数是否小于1(不大于0)
        // 是：抛出UpdateException
        Integer nonRows = addressMapper.updateNonDefaultByUid(uid);
        if (nonRows < 1) {
            throw new UpdateException("设置默认地址发生异常");
        }
        // 调用addressMapepr的updateDefaultByAid()将指定aid的收货地址设置为默认，并获取返回的受影响的行数
        // 判断受影响的行数是否不为1
        // 是：抛出UpdateException
        Date date = new Date();
        Integer rows = addressMapper.updateDefaultByAid(aid, username, date);
        if (rows < 1) {
            throw new UpdateException("设置默认收货地址发生未知异常");
        }
    }

    @Override
    public void deleteByAid(Integer aid, Integer uid, String username) {
        // 根据参数aid，调用findByAid()查询收货地址数据
        // 判断查询结果是否为null
        Address result = addressMapper.findAddressByAid(aid);
        if (result == null) {
            throw new AddressNotFoundException("地址未找到");
        }
        // 是：抛出AddressNotFoundException
        // 判断查询结果中的uid与参数uid是否不一致(使用equals()判断)
        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("非法访问");
        }
        // 是：抛出AccessDeniedException：非法访问
        // 根据参数aid，调用deleteByAid()执行删除
        Integer rows = addressMapper.deletedAddressByAid(aid);
        if (rows != 1) {
            throw new DeleteException("删除未知异常");
        }
        // 判断查询结果中的isDefault是否为0
        if (result.getIsDefault() == 0) {
            return;
        }

        // 调用持久层的countByUid()统计目前还有多少收货地址
        Integer count = addressMapper.countAddressesByUid(uid);
        // 判断目前的收货地址的数量是否为0
        if (count == 0) {
            return;
        }

        // 调用findLastModified()找出用户最近修改的收货地址数据
        Address address= addressMapper.findLastModifiedTimeByUid(uid);
        // 从以上查询结果中找出aid属性值
         Integer aid1=address.getAid();
        // 调用持久层的updateDefaultByAid()方法执行设置默认收货地址，并获取返回的受影响的行数
        Date date=new Date();
         Integer rows1=addressMapper.updateDefaultByAid(aid,username,date);
        // 判断受影响的行数是否不为1
           if (rows1!=1){
               throw new UpdateException("更新默认值发生异常");
           }
        // 是：抛出UpdateException
    }

    @Override
    public Address getAddressByAidAndUid(Integer aid, Integer uid) {
        Address address=addressMapper.findAddressByAid(aid);
        if (address==null){
            throw new AddressNotFoundException("地址为找到");
        }

        if (!address.getUid().equals(uid)){
            throw new AccessDeniedException("拒绝访问");
        }

        address.setProvinceCode(null);
        address.setCityCode(null);
        address.setAreaCode(null);
        address.setCreatedUser(null);
        address.setCreatedTime(null);
        address.setModifiedUser(null);
        address.setModifiedTime(null);

        return address;
    }
}
