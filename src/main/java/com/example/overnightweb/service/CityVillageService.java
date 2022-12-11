package com.example.overnightweb.service;


import com.example.common.entity.CityVillage;
import com.example.common.entity.Region;
import com.example.common.repository.CityVillageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CityVillageService {
    private final CityVillageRepository cityVillageRepository;

    public void addCityVillage(CityVillage cityVillage) {
        cityVillageRepository.save(cityVillage);
    }

    public List<CityVillage> getAll() {
        List<CityVillage> cityVillageList = cityVillageRepository.findAll();
        return cityVillageList;
    }

    public Optional<CityVillage> getById(int cityVillageId) {
        return cityVillageRepository.findById(cityVillageId);
    }

    public void updateCityVillage(int cityVillageId, String cityVillageName, Region region) {
        Optional<CityVillage> cityVillageById = cityVillageRepository.findById(cityVillageId);
        if (cityVillageById.isPresent()) {
            CityVillage cityVillage = cityVillageById.get();
            cityVillage.setName(cityVillageName);
            cityVillage.setRegion(region);
        }
    }

    public void deleteById(int cityVillageId) {
        cityVillageRepository.deleteById(cityVillageId);
    }
}
