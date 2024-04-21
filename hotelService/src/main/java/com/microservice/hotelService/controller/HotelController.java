package com.microservice.hotelService.controller;

import com.microservice.hotelService.entity.Hotel;
import com.microservice.hotelService.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hotels/all")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @GetMapping("/gethotels")
    public List<Hotel> getHotels(){
        return hotelService.getHotels();
    }

    @GetMapping("/gethotels/{id}")
    public Hotel getHotel(@PathVariable int id){
        return hotelService.getHotelById(id);
    }
}
