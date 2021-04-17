package com.example.demo.controller

import com.example.demo.Utilidades.CustomRequest
import com.example.demo.services.TorreService
import com.sun.javafx.collections.MappingChange
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
    def getAllTorres(@RequestBody Map  data) {

        Map MapData = data

        println(data)

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){
            MapData = MapData.data;

            return torreService.GetAllTorre(MapData.id as int)
        }
        else{
            return MyCustomsRequests.TokenNoValido();
        }
    }

    @RequestMapping(value="/torre/insert", method = RequestMethod.POST)
    def InsertTorres(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){
            MapData = MapData.data;

            if(torreService.Insert(MapData.idresidencial as int, MapData.nombre as String, MapData.niveles as int)){
                return MyCustomsRequests.MessageSuccess()
            }
            else{
                return MyCustomsRequests.MessageFailed()
            }
        }
        else{
            return MyCustomsRequests.TokenNoValido();
        }
    }

    @RequestMapping(value="/torre/update", method = RequestMethod.POST)
    def UpdateTorres(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){
            MapData = MapData.data;

            if(torreService.Update(MapData.idresidencial as int, MapData.nombre as String, MapData.niveles as int, MapData.id as int)){
                return MyCustomsRequests.MessageSuccess()
            }
            else{
                return MyCustomsRequests.MessageFailed()
            }
        }
        else{
            return MyCustomsRequests.TokenNoValido();
        }
    }

}
