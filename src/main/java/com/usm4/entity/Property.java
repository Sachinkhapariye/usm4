package com.usm4.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "property")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "property_name", nullable = false)
    private String propertyName;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @Column(name = "bedrooms", nullable = false)
    private Integer bedrooms;

    @Column(name = "bathrooms" ,nullable = false)
    private Integer bathrooms;

    @Column(name = "beds", nullable = false)
    private Integer beds;

    @Column(name = "guest", nullable = false)
    private Integer guest;

    @Column(name = "nightly_price", nullable = false)
    private Integer nightlyPrice;

}