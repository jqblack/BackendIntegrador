package com.example.demo.controller

import com.example.demo.Utilidades.CustomRequest
import com.example.demo.services.QuejasService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class QuejasController {

    @Autowired
    QuejasService quejasService

    CustomRequest MyCustomsRequests = new CustomRequest();

    @RequestMapping(value="/quejas/getquejasbyresidencial", method = RequestMethod.POST)
    def Getmantenimientos(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){

            MapData = MapData.data;

            return quejasService.GetTiposQuejas(MapData.idResi as int)
        }
        else{
            return MyCustomsRequests.TokenNoValido();
        }
    }

    @RequestMapping(value="/quejas/getquejasbyinqui", method = RequestMethod.POST)
    def GetTipoQuejasByInquilino(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){

            MapData = MapData.data;

            return quejasService.GetTipoQuejasByInquilino(MapData.idUser as int)
        }
        else{
            return MyCustomsRequests.TokenNoValido();
        }
    }

    @RequestMapping(value="/quejas/cargarpresunto", method = RequestMethod.POST)
    def cargarpresunto(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){

            MapData = MapData.data;

            return quejasService.cargarpresunto(MapData.idUser as int)
        }
        else{
            return MyCustomsRequests.TokenNoValido();
        }
    }

    @RequestMapping(value="/quejas/listaquejasbyuser", method = RequestMethod.POST)
    def listaquejasbyuser(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){

            MapData = MapData.data;

            return quejasService.GetListQuejasbyResidencial(MapData.idUser as int)
        }
        else{
            return MyCustomsRequests.TokenNoValido();
        }
    }


    @RequestMapping(value="/quejas/insert", method = RequestMethod.POST)
    def insertQuejas(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){

            MapData = MapData.data;

            if(quejasService.Insert(
                    MapData.descripcion as String,
                    MapData.cantAdvertencia as int,
                    MapData.limite as int,
                    MapData.costo as int,
                    MapData.diriguido as int,
                    MapData.idResi as int)){

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

    @RequestMapping(value="/quejas/update", method = RequestMethod.POST)
    def updateQuejas(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){

            MapData = MapData.data;

            if(quejasService.Update(MapData.descripcion as String,MapData.cantAdvertencia as int, MapData.limite as int,
                    MapData.costo as int, MapData.diriguido as int, MapData.idresi as int,MapData.idTipoqueja as int)){

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

    @RequestMapping(value="/quejas/inserttouserqueja", method = RequestMethod.POST)
    def InsertToUserQuejas(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){

            MapData = MapData.data;

            if(quejasService.InsertQueja(
                    MapData.idTipoqueja as int,
                    MapData.idUserfrom as int,
                    MapData.idUserto as int,
                    MapData.descripcion as String,
                    MapData.idUser as int,
                    MapData.username as String)){

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
