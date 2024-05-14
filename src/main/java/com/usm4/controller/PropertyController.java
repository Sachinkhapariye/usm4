package com.usm4.controller;

import com.usm4.entity.Property;
import com.usm4.exception.ResourcesNotFound;
import com.usm4.repository.PropertyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/property")
public class PropertyController {

    private PropertyRepository propertyRepository;

    public PropertyController(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @GetMapping("/{locationName}")
    public ResponseEntity<List<Property>> getPropertyListing(@PathVariable String locationName){
        List<Property> properties = propertyRepository.listPropertyByLocationAndCountry(locationName);
        return new ResponseEntity<>(properties, HttpStatus.OK);
    }


    //Exception Handling globally

    @GetMapping("/{propertyId}")
    public ResponseEntity<Property> getPropertyById(@PathVariable long propertyId){
        Property property = propertyRepository.findById(propertyId).orElseThrow(
                ()-> new ResourcesNotFound("Property not found with id "+propertyId)
        );

        return new ResponseEntity<>(property,HttpStatus.OK);
    }



    //Pagination

    @GetMapping("/findAllProperty")
    public ResponseEntity<List<Property>> getAllProperty(
            @RequestParam(name = "pageSize",defaultValue = "5",required = false) int pageSize,
            @RequestParam(name = "pageNo", defaultValue = "0",required = false)int pageNo
    ){
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<Property> all = propertyRepository.findAll(pageable);
        List<Property> properties = all.getContent();
        return new ResponseEntity<>(properties,HttpStatus.OK);
    }



}
