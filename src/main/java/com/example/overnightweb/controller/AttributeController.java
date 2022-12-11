package com.example.overnightweb.controller;


import com.example.common.entity.Attribute;
import com.example.overnightweb.service.AdminService;
import com.example.overnightweb.service.AttributeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AttributeController {

    private final AdminService adminService;
    private final AttributeService attributeService;

    @GetMapping("/attribute")
    public String attribute(@RequestParam(value = "keyword", required = false) String keyword,
                            ModelMap modelMap) {
        List<Attribute> allAttribute = attributeService.findAllAttribute(keyword);
        modelMap.addAttribute("allAttribute", allAttribute);

        return "admin/adminPageAttribute";
    }

    @PostMapping("/attribute/add")
    public String add(@ModelAttribute Attribute attribute) {
        attributeService.attributeSave(attribute);
        return "redirect:/attribute";
    }

    @GetMapping("/attribute/{id}")
    public String delete(@PathVariable int id) {
        attributeService.deleteAttributeById(id);
        return "redirect:/attribute";
    }
}
