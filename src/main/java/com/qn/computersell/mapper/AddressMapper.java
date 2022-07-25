package com.qn.computersell.mapper;

import com.qn.computersell.entity.Address;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface AddressMapper {

    Integer countAddressesByUid(Integer uid);

    Integer insertAddress(Address address);

    /**
     * @param uid
     * @return
     */
    List<Address> findAddressByUid(Integer uid);

    /**
     * @param uid
     * @return
     */
    Integer updateNonDefaultByUid(Integer uid);

    /**
     * @param aid
     * @param modifiedUser
     * @param modifiedTime
     * @return
     */
    Integer updateDefaultByAid(@Param("aid") Integer aid, @Param("modifiedUser") String modifiedUser, @Param("modifiedTime") Date modifiedTime);

    /**
     * @param aid
     * @return
     */
    Address findAddressByAid(Integer aid);

    /**
     * 查找用户最近的一次修改的收货地址
     *
     * @param uid
     * @return
     */
    Address findLastModifiedTimeByUid(Integer uid);

    /**
     * 删除数据
     *
     * @param aid
     * @return
     */
    Integer deletedAddressByAid(Integer aid);
}
