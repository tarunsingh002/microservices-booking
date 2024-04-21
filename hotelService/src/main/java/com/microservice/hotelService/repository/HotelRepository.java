package com.microservice.hotelService.repository;

import com.microservice.hotelService.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel,Integer> {
}
