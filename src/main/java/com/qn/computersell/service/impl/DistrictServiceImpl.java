package com.qn.computersell.service.impl;

import com.qn.computersell.entity.District;
import com.qn.computersell.mapper.DistrictMapper;
import com.qn.computersell.service.IDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DistrictServiceImpl implements IDistrictService {
    @Autowired
    private DistrictMapper districtMapper;
    @Override
    public List<District> getDistrictsByParent(String parent) {
         List<District> districts=districtMapper.findDistrictByParent(parent);

         for(District district:districts)
         {
             district.setId(null);
             district.setParent(null);
         }
         return districts;
    }

    @Override
    public String getNameByCode(String cityCode) {
        return districtMapper.findNameByCode(cityCode);
    }
}
