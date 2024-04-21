package com.microservice.hotelService.controller;

import com.microservice.hotelService.dto.ValidateTokenResponse;
import com.microservice.hotelService.entity.Hotel;
import com.microservice.hotelService.externalService.UserService;
import com.microservice.hotelService.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/hotels/admin")
public class AdminController {

    @Autowired
    private HotelService hotelService;

    @Autowired
    UserService userService;

    @PostMapping("/addhotel")
    public Hotel addHotel(@RequestBody Hotel hotel, @RequestHeader("Authorization") String token){
        if (token==null || !token.startsWith("Bearer "))
            return null;
        ValidateTokenResponse res = userService.validateToken(token.substring(7));
        if (!res.isTokenIsValid() || !res.isAdmin())
            return null;
        return hotelService.addHotel(hotel);
    }

    @PutMapping("/updatehotel/{id}")
    public Hotel updateHotel(@RequestBody Hotel hotel, @PathVariable int id,@RequestHeader("Authorization") String token){
        if (token==null || !token.startsWith("Bearer "))
            return null;
        ValidateTokenResponse res = userService.validateToken(token.substring(7));
        if (!res.isTokenIsValid() || !res.isAdmin())
            return null;
        return hotelService.updateHotel(id,hotel);
    }

    @DeleteMapping("/deletehotel/{id}")
    public String deleteHotel(@PathVariable int id,@RequestHeader("Authorization") String token){
        if (token==null || !token.startsWith("Bearer "))
            return null;
        ValidateTokenResponse res = userService.validateToken(token.substring(7));
        if (!res.isTokenIsValid() || !res.isAdmin())
            return null;
        return hotelService.deleteHotelById(id);
    }

}
