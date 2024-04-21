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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/booking/admin")
public class AdminController {

    @Autowired
    BookingService bookingService;

    @Autowired
    HotelService hotelService;

    @Autowired
    UserService userService;

    @GetMapping("/getbookings")
    public List<BookingDto> getBookings(@RequestHeader("Authorization") String token){
        if (token==null || !token.startsWith("Bearer "))
            return null;
        ValidateTokenResponse res = userService.validateToken(token.substring(7));
        if (!res.isTokenIsValid() || !res.isAdmin())
            return null;

        List<Booking> bookings = bookingService.getBookings();

        List<BookingDto> bookingDtos = new ArrayList<>();

        for (Booking booking:bookings){
//            System.out.println(token);
            User user = userService.getUser(booking.getUserId(), token);
            Hotel hotel = hotelService.getHotel(booking.getHotelId());
            BookingDto bookingDto = BookingDto.builder().user(user)
                    .toDate(booking.getToDate())
                    .fromDate(booking.getFromDate())
                    .roomsQuantity(booking.getRoomsQuantity())
                    .id(booking.getId())
                    .totalAmount(booking.getTotalAmount())
                    .hotel(hotel).build();
            bookingDtos.add(bookingDto);
        }

        return bookingDtos;
    }
}
