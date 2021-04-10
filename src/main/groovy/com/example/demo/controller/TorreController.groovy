package com.example.demo.controller

import com.example.demo.Utilidades.CustomRequest
import com.example.demo.services.TorreService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class TorreController {

    @Autowired
    TorreService torreService

    CustomRequest MyCustomsRequests = new CustomRequest();

    @RequestMapping(value="/torre/gettorres", method = RequestMethod.POST)
    def getAllResidencial(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){
            MapData = MapData.data;

            return torreService.GetAllTorre(MapData.id as int)
        }
        else{
            return MyCustomsRequests.TokenNoValido();
        }
    }

}
