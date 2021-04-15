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

            if(areasComunesServices.Insert(MapData.descripcion as String, MapData.idResi as int, MapData.nombre as String)){

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

    @RequestMapping(value="/areas/update", method = RequestMethod.POST)
    def UpdateAreas(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){

            MapData = MapData.data;

            if(areasComunesServices.Update(MapData.idArea as int, MapData.descripcion as String)){

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

    @RequestMapping(value="/areas/delete", method = RequestMethod.POST)
    def DeleteAreas(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){

            MapData = MapData.data;

            if(areasComunesServices.Delete(MapData.idArea as int)){

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

    @RequestMapping(value="/areas/getAreasbyResidencial", method = RequestMethod.POST)
    def getAreasbyResidencial(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){

            MapData = MapData.data;

            areasComunesServices.GetAreasComunes(MapData.idResi as int)
        }
        else{
            return MyCustomsRequests.TokenNoValido();
        }
    }
}
