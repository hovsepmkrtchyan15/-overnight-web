package com.example.overnightweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductGalleryController {

    @GetMapping("product-gallery")
    public String productGallery(){
        return "product-gallery";
    }
}
