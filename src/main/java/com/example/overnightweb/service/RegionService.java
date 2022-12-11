package com.example.overnightweb.service;


import com.example.common.entity.Region;
import com.example.common.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Service
public class RegionService {
    private final RegionRepository regionRepository;
    public void addRegion(Region region) {
        regionRepository.save(region);
    }
    public List<Region> getAll(String keyword) {
        if(keyword==null) {
            return regionRepository.findAll();
        }else return regionRepository.findByNameContaining(keyword);
    }
    public void deleteById(int regionId) {
        regionRepository.deleteById(regionId);
    }
    public Optional<Region> getById(int regionId) {
        return regionRepository.findById(regionId);
    }
    public void updateRegion(int regionId, String regionName) {
        Optional<Region> regionById = regionRepository.findById(regionId);
        if (regionById.isPresent()) {
            Region region = regionById.get();
            region.setName(regionName);
            regionRepository.save(region);
        }
    }
    public List<Region> findAll() {
        return regionRepository.findAll();

    }
}
