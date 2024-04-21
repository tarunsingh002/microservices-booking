package com.microservice.bookingService.controller;

import com.microservice.bookingService.dto.BookingDto;
import com.microservice.bookingService.dto.ValidateTokenResponse;
import com.microservice.bookingService.entity.Booking;
import com.microservice.bookingService.entity.Hotel;
import com.microservice.bookingService.entity.User;
import com.microservice.bookingService.externalService.HotelService;
import com.microservice.bookingService.externalService.UserService;
import com.microservice.bookingService.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/booking/user")
public class UserController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private UserService userService;

    @PostMapping("/createbooking")
    public Booking createBooking(@RequestBody Booking booking, @RequestHeader("Authorization") String token){
        if (token==null || !token.startsWith("Bearer "))
            return null;
        ValidateTokenResponse res = userService.validateToken(token.substring(7));
        if (!res.isTokenIsValid() || res.isAdmin())
            return null;
        return bookingService.createBooking(booking);
    }

    @GetMapping("/getbooking/{id}")
    public BookingDto getBooking(@PathVariable int id,@RequestHeader("Authorization") String token){
        if (token==null || !token.startsWith("Bearer "))
            return null;
        ValidateTokenResponse res = userService.validateToken(token.substring(7));
        if (!res.isTokenIsValid() || res.isAdmin())
            return null;

        Booking booking = bookingService.getBookingById(id);
        Hotel hotel = hotelService.getHotel(booking.getHotelId());
        User user = userService.getUser(booking.getUserId(), token);
        BookingDto bookingDto = BookingDto.builder().user(user)
                .toDate(booking.getToDate())
                .fromDate(booking.getFromDate())
                .roomsQuantity(booking.getRoomsQuantity())
                .id(booking.getId())
                .totalAmount(booking.getTotalAmount())
                .hotel(hotel).build();

        return bookingDto;
    }
}
