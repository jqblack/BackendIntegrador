package com.example.demo.controller

import com.example.demo.Utilidades.CustomRequest
import com.example.demo.services.SolicitudService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class SolicitudController {

    @Autowired
    SolicitudService solicitudService

    CustomRequest MyCustomsRequests = new CustomRequest();

    @RequestMapping(value="/solicitud/getfirstlist", method = RequestMethod.POST)
    def getfirstlist(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){
            MapData = MapData.data;
            return solicitudService.getfirstlist()
        }
        else{
            return MyCustomsRequests.TokenNoValido();
        }
    }

    @RequestMapping(value="/solicitud/getlistfilter", method = RequestMethod.POST)
    def getlistfilter(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){
            MapData = MapData.data;

            return solicitudService.getFilterList(MapData.idPro as int, MapData.precio as int)
        }
        else{
            return MyCustomsRequests.TokenNoValido();
        }
    }
}
