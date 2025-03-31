package com.example.springboot.service;

import com.example.springboot.repository.entity.DistrictEntity;
import com.example.springboot.repository.entity.ProvinceEntity;
import com.example.springboot.repository.entity.WardEntity;

import java.util.List;

public interface LocationService {
    public List<ProvinceEntity> findAllProvinces();
    public List<DistrictEntity> findDistrictsByProvinceCode(String province);
    public List<WardEntity> findWardsByDistrictCode(String district);
}
