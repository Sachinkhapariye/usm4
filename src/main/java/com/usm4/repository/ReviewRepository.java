package com.usm4.repository;

import com.usm4.entity.AppUser;
import com.usm4.entity.Property;
import com.usm4.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select r from Review r where r.property=:property and r.appUser=:appUser")
    Review fetchUserReview(@Param("property") Property property,@Param("appUser") AppUser appUser);
}