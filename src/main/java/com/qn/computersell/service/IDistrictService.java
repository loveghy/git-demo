package com.qn.computersell.service;


import com.qn.computersell.entity.District;

import java.util.List;

public interface IDistrictService {
    List<District> getDistrictsByParent(String parent);


    String getNameByCode(String cityCode);
}
