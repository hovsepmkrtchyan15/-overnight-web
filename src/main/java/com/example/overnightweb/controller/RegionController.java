package com.example.overnightweb.controller;


import com.example.common.entity.Region;
import com.example.overnightweb.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class RegionController {
    private final RegionService regionService;

    @PostMapping("/region/add")
    public String addRegion(@ModelAttribute Region region) {
        regionService.addRegion(region);
        return "redirect:/region";
    }
    @GetMapping("/region")
    public String getAllRegions(@RequestParam(value = "keyword", required = false) String keyword,
                                ModelMap modelMap) {
        List<Region> regions = regionService.getAll(keyword);
        modelMap.addAttribute("regions", regions);
        return "/admin/adminPageRegion";
    }
    @GetMapping("/region/id")
    public String getRegionById(@RequestParam int regionId, ModelMap modelMap) {
        Optional<Region> region=regionService.getById(regionId);
        modelMap.addAttribute("region", region);
        return "/regions";
    }
    @PostMapping("/region/update")
    public String updateRegion (@RequestParam("regionId") int regionId,
                                @RequestParam("regionName") String regionName ){
        regionService.updateRegion(regionId,regionName);
        return "redirect:/regions";
    }
    @GetMapping("/region/{id}")
    public String deleteRegion(@PathVariable int id) {
        regionService.deleteById(id);
        return "redirect:/region";
    }



}
