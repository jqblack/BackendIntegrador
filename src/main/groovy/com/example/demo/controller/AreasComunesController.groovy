package com.example.demo.controller

import com.example.demo.Utilidades.CustomRequest
import com.example.demo.services.AreasComunesServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class AreasComunesController {

    @Autowired
    AreasComunesServices areasComunesServices

    CustomRequest MyCustomsRequests = new CustomRequest();

    @RequestMapping(value="/areas/insert", method = RequestMethod.POST)
    def insertAreas(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){

            MapData = MapData.data;

            if(){
                return MyCustomsRequests.MessageSuccess();
            }
            else{
                return MyCustomsRequests.MessageFailed();
            }
        }
        else{
            return MyCustomsRequests.TokenNoValido();
        }
    }
}
