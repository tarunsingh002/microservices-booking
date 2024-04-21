package com.microservice.bookingService.repository;

import com.microservice.bookingService.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,Integer> {

}
