package com.microservice.bookingService.externalService;

import com.microservice.bookingService.entity.Hotel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "hotel-service")
public interface HotelService {

    @GetMapping("/api/v1/hotels/all/gethotels/{id}")
    public Hotel getHotel(@PathVariable int id);
}
