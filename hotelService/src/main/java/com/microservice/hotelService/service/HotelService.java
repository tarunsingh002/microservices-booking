package com.microservice.hotelService.service;

import com.microservice.hotelService.entity.Hotel;
import com.microservice.hotelService.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    public Hotel addHotel(Hotel hotel){
        return hotelRepository.save(hotel);
    }

    public List<Hotel> getHotels(){
        return hotelRepository.findAll();
    }

    public Hotel getHotelById(int id){
        return hotelRepository.findById(id).orElse(null);
    }

    public String deleteHotelById(int id){
        if (hotelRepository.existsById(id)){
            hotelRepository.deleteById(id);
            return "Hotel deleted";
        } else return "Hotel requested to be deleted does not exist";
    }

    public Hotel updateHotel(int id, Hotel hotel){
        Hotel existingHotel = hotelRepository.findById(id).orElse(null);
        if (existingHotel!=null){
            existingHotel.setName(hotel.getName());
            existingHotel.setAmenities(hotel.getAmenities());
            existingHotel.setDescription(hotel.getDescription());
            existingHotel.setLocation(hotel.getLocation());
            existingHotel.setPrice(hotel.getPrice());
            existingHotel.setImageUrl(hotel.getImageUrl());
            return hotelRepository.save(existingHotel);
        }
        else return null;
    }








}
