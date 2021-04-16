package com.example.demo.controller

import com.example.demo.Utilidades.CustomRequest
import com.example.demo.services.MantenimientoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class MantenimientoController {


    @Autowired
    MantenimientoService mantenimientoService

    CustomRequest MyCustomsRequests = new CustomRequest();

    @RequestMapping(value="/mantenimientos/insert", method = RequestMethod.POST)
    def insertMantenimiento(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){

            MapData = MapData.data;

            if(mantenimientoService.Insert(MapData.descripcion as String, MapData.dias as int, MapData.listidResi as List)){

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

    @RequestMapping(value="/mantenimientos/update", method = RequestMethod.POST)
    def updateMantenimiento(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){

            MapData = MapData.data;

            if(mantenimientoService.Update(MapData.idMantenimiento as int, MapData.dias as int)){

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

    @RequestMapping(value="/mantenimientos/getmantenimientos", method = RequestMethod.POST)
    def Getmantenimientos(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){

            MapData = MapData.data;

            return mantenimientoService.GetMantenimientos(MapData.idResi as int)
        }
        else{
            return MyCustomsRequests.TokenNoValido();
        }
    }


}
