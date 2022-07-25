package com.qn.computersell.mapper;


import com.qn.computersell.entity.District;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DistrictMapper {
    List<District> findDistrictByParent(String parent);

    String findNameByCode(String code);
}
