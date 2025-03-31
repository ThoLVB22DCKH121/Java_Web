package com.example.springboot.api;

import com.example.springboot.repository.entity.DistrictEntity;
import com.example.springboot.repository.entity.WardEntity;
import com.example.springboot.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiLocation {
    @Autowired
    private LocationService locationService;
    @GetMapping("/api/districts")
    public List<DistrictEntity> getDistricts(@RequestParam("province_code") String provinceCode) {
        System.out.println(provinceCode);
        List<DistrictEntity> list = locationService.findDistrictsByProvinceCode(provinceCode);
        System.out.println(list.size());
        return locationService.findDistrictsByProvinceCode(provinceCode);
    }

    @GetMapping("/api/wards")
    public List<WardEntity> getWards(@RequestParam("district_code") String districtCode) {
        return locationService.findWardsByDistrictCode(districtCode);
    }
}
