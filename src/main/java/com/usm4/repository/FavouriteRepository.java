package com.usm4.repository;

import com.usm4.entity.AppUser;
import com.usm4.entity.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FavouriteRepository extends JpaRepository<Favourite, Long> {

    @Query("select f from Favourite f where f.appUser=:appUser")
    public List<Favourite> getFavourites(@Param("appUser") AppUser appUser);

}