package com.example.overnightweb.service;


import com.example.common.entity.Attribute;
import com.example.common.repository.AttributeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttributeService {

    private final AttributeRepository attributeRepository;

    public void attributeSave(Attribute attribute) {
        attributeRepository.save(attribute);
    }

    public List<Attribute> findAllAttribute(String keyword) {
        if (keyword == null) {
            return attributeRepository.findAll();
        } else return attributeRepository.findByNameContaining(keyword);
    }

    public void deleteAttributeById(int id) {
       attributeRepository.deleteById(id);
    }
}
