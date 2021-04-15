package com.example.demo.controller

import com.example.demo.Utilidades.CustomRequest
import com.example.demo.services.ServiciosService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class ServiciosController {

    @Autowired
    ServiciosService serviciosService

    CustomRequest MyCustomsRequests = new CustomRequest();

    @RequestMapping(value="/servicios/getservicios", method = RequestMethod.POST)
    def getAllServicios(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){
            MapData = MapData.data;

            return serviciosService.GetAllServices(MapData.idResi as int)
        }
        else{
            return MyCustomsRequests.TokenNoValido();
        }
    }

    @RequestMapping(value="/servicios/insert", method = RequestMethod.POST)
    def InsertServicio(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){
            MapData = MapData.data;

            if(serviciosService.Insert(MapData.descripcion as String,MapData.cobro as int, MapData.pago as int,MapData.idResi as int)){

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

    @RequestMapping(value="/servicios/update", method = RequestMethod.POST)
    def UpdateServicio(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){
            MapData = MapData.data;

            if(serviciosService.Update(MapData.descripcion as String,MapData.cobro as int, MapData.pago as int,MapData.idresidencial as int, MapData.id as int)){

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
