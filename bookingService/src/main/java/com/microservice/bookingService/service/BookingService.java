package com.microservice.bookingService.service;

import com.microservice.bookingService.entity.Booking;
import com.microservice.bookingService.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepository repository;


    public Booking createBooking(Booking booking) {
        return repository.save(booking);
    }

    public Booking getBookingById(int id){
        return repository.findById(id).orElse(null);
    }

    public List<Booking> getBookings(){
        return repository.findAll();
    }
}
