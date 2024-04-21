package com.microservice.bookingService.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hotel {
    int id;
    String name;
    String location;
    String description;
    String amenities;
    String imageUrl;
    float price;
}
