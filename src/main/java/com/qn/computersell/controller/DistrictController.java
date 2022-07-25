package com.qn.computersell.controller;

import com.qn.computersell.entity.District;
import com.qn.computersell.service.IDistrictService;
import com.qn.computersell.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/districts")
public class DistrictController extends BaseController{

    @Autowired
    private IDistrictService districtService;

     @RequestMapping({"","/"})
    public JsonResult<List<District>> getDistrictsByParent(String parent)
     {
         List<District> districts=districtService.getDistrictsByParent(parent);
         return new JsonResult<>(OK,districts);
     }
}
