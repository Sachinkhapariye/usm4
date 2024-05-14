package com.usm4.repository;

import com.usm4.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    public List<Image> findByPropertyId(long propertyId);

}