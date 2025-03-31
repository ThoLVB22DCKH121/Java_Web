package com.example.springboot.service.Impl;

import com.example.springboot.repository.DistrictRepository;
import com.example.springboot.repository.ProvinceRepository;
import com.example.springboot.repository.WardRepository;
import com.example.springboot.repository.entity.DistrictEntity;
import com.example.springboot.repository.entity.ProvinceEntity;
import com.example.springboot.repository.entity.WardEntity;
import com.example.springboot.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class LocationServiceImpl implements LocationService {
    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private WardRepository wardRepository;

    public List<ProvinceEntity> findAllProvinces() {
        return provinceRepository.findAll();
    }

    public List<DistrictEntity> findDistrictsByProvinceCode(String provinceCode) {
        List<DistrictEntity> districts = districtRepository.findAll();
        List<DistrictEntity> result = new ArrayList<>();
        for (DistrictEntity district : districts) {
            if(district.getProvince().getCode().equals(provinceCode)) {
                result.add(district);
            }
        }
        return result;
    }

    public List<WardEntity> findWardsByDistrictCode(String districtCode) {
        List<WardEntity> wards = wardRepository.findAll();
        List<WardEntity> result = new ArrayList<>();
        for (WardEntity ward : wards) {
            if(ward.getDistrict().getCode().equals(districtCode)) {
                result.add(ward);
            }
        }
        return result;
    }
}
