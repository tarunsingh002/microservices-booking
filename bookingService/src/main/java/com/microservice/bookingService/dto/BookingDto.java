package com.microservice.bookingService.dto;

import com.microservice.bookingService.entity.Hotel;
import com.microservice.bookingService.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingDto {
    int id;
    long fromDate;
    long toDate;
    int roomsQuantity;
    float totalAmount;
    Hotel hotel;
    User user;
}
