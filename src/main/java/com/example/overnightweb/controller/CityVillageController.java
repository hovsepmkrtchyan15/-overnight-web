package com.example.overnightweb.controller;


import com.example.common.entity.CityVillage;
import com.example.common.entity.Region;
import com.example.overnightweb.service.CityVillageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
@Controller
@RequiredArgsConstructor
public class CityVillageController {
    private final CityVillageService cityVillageService;
    @PostMapping("/cityVillage/add")
    public String addCityVillage(@ModelAttribute CityVillage cityVillage) {
        cityVillageService.addCityVillage(cityVillage);
        return "redirect:/cityVillagies";
    }
    @GetMapping("/cityVillage")
    public String getAllCityVillage(ModelMap modelMap) {
        List<CityVillage> cityVillages = cityVillageService.getAll();
        modelMap.addAttribute("cityVillages", cityVillages);
        return "/cityVillagies";
    }
    @GetMapping("/cityVillage/id")
    public String getCityVillageById(@RequestParam ("cityVillageId") int cityVillageId, ModelMap modelMap) {
        Optional<CityVillage> cityVillage= cityVillageService.getById(cityVillageId);
        modelMap.addAttribute("cityVillage", cityVillage);
        return "/cityVillagies";
    }
    @PostMapping("/cityVillage/update")
    public String updateCityVillage (@RequestParam("cityVillageId") int cityVillageId,
                                @RequestParam("cityVillageName") String cityVillageName,
                                @RequestParam("regionId") Region region){
        cityVillageService.updateCityVillage(cityVillageId,cityVillageName,region);
        return "redirect:/cityVillagies";
    }
    @GetMapping("/cityVillage/delete")
    public String deleteCityVillage(@RequestParam("cityVillageId") int cityVillageId) {
        cityVillageService.deleteById(cityVillageId);
        return "redirect:/cityVillagies";
    }
}
