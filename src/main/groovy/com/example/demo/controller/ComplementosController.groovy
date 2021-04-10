package com.example.demo.controller

import com.example.demo.Utilidades.CustomRequest
import com.example.demo.services.ComplementosService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class ComplementosController {

    CustomRequest MyCustomsRequests = new CustomRequest();

    @Autowired
    ComplementosService complementosService

    @RequestMapping(value="/complementos/get_provincias", method = RequestMethod.POST)
    def Get_Provincias(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){
            return complementosService.Get_Provincias()
        }
        else{
            return MyCustomsRequests.TokenNoValido();
        }
    }

    @RequestMapping(value="/complementos/get_municipio", method = RequestMethod.POST)
    def get_municipio(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){
            return complementosService.Get_Municipio()
        }
        else{
            return MyCustomsRequests.TokenNoValido();
        }
    }

    @RequestMapping(value="/complementos/get_sector", method = RequestMethod.POST)
    def get_sector(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){
            return complementosService.Get_Sector()
        }
        else{
            return MyCustomsRequests.TokenNoValido();
        }
    }

    @RequestMapping(value="/complementos/test", method = RequestMethod.GET)
    def test() {

        Map mapa = [:]
        if(mapa == [:]){
            return "Randiel"
        }else{
            return "test"
        }

    }
}
