package com.usm4.controller;

import com.usm4.entity.AppUser;
import com.usm4.entity.Favourite;
import com.usm4.entity.Property;
import com.usm4.repository.FavouriteRepository;
import com.usm4.repository.PropertyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/favourite")
public class FavouriteController {

    private FavouriteRepository favouriteRepository;
    private PropertyRepository propertyRepository;

    public FavouriteController(FavouriteRepository favouriteRepository, PropertyRepository propertyRepository) {
        this.favouriteRepository = favouriteRepository;
        this.propertyRepository = propertyRepository;
    }

    @PostMapping("/addFavourite")
    public ResponseEntity<Favourite> addFavourite(
            @AuthenticationPrincipal AppUser appUser,
            @RequestParam long propertyId
            ){
        Optional<Property> byId = propertyRepository.findById(propertyId);
        Property property = byId.get();
        Favourite favourite = new Favourite();
        favourite.setAppUser(appUser);
        favourite.setProperty(property);
        favourite.setIsFavourite(true);
        Favourite saveFavourite = favouriteRepository.save(favourite);
        return new ResponseEntity<>(saveFavourite, HttpStatus.CREATED);
    }

    @GetMapping("/userFavouriteList")
    public ResponseEntity< List<Favourite>> getAllFavouriteOfUser(@AuthenticationPrincipal AppUser appUser){
        List<Favourite> favourites = favouriteRepository.getFavourites(appUser);
        return new ResponseEntity<>(favourites,HttpStatus.OK);
    }
}
