package com.example.demo.controller

import com.example.demo.Utilidades.CustomRequest
import com.example.demo.services.ResidencialService
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class residencial {

    @Autowired
    ResidencialService residencialservice

    CustomRequest MyCustomsRequests = new CustomRequest();


    @RequestMapping(value="/residencial/insert", method = RequestMethod.POST)
    def InsertResidencial(@RequestBody String  data) {

        JsonSlurper parser = new JsonSlurper()
        Map MapData = parser.parseText(data);

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){

            MapData = MapData.data;

            if(residencialservice.InsertResidencial(MapData.nombre as String,
                    MapData.provincia as int,
                    MapData.municipio as int,
                    MapData.sector as int,MapData.area as int)){

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

    @RequestMapping(value="/residencial/update", method = RequestMethod.POST)
    def UpdateResidencial(@RequestBody String  data) {

        JsonSlurper parser = new JsonSlurper()
        Map MapData = parser.parseText(data);

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){

            MapData = MapData.data;

            if(residencialservice.UpdateResidencial(MapData.nombre as String,
                    MapData.provincia as int,
                    MapData.municipio as int,
                    MapData.sector as int,
                    MapData.area as int,
                    MapData.ID_residencial as int)){

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

    @RequestMapping(value="/residencial/delete", method = RequestMethod.POST)
    def DeleteResidencial(@RequestBody String  data) {

        JsonSlurper parser = new JsonSlurper()
        Map MapData = parser.parseText(data);

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){

            MapData = MapData.data;

            if(residencialservice.DeleteResidencial(MapData.ID_residencial as int)){

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

    @RequestMapping(value="/residencial/ownresidenciales", method = RequestMethod.POST)
    def OwnResidencial(@RequestBody String  data) {

        JsonSlurper parser = new JsonSlurper()
        Map MapData = parser.parseText(data);

        if(MapData.key == "pasame"){
            println("KLOK PAPA");
        }
        else{
            println("Brrrr");
        }

        return residencialservice.getResidencialList();
    }

    @RequestMapping(value="/residencial/test", method = RequestMethod.POST)
    def InsertTest(@RequestBody Map  data) {

        println(data);
       // JsonSlurper parser = new JsonSlurper()
        //Map MapData = parser.parseText(data);

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){

            MapData = MapData.data as Map

           if(residencialservice.TestInsert(MapData.midescri as String)){
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

    @RequestMapping(value="/residencial/get_provincias", method = RequestMethod.POST)
    def Get_Provincias(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){
            return residencialservice.Get_Provincias()
        }
        else{
            return MyCustomsRequests.TokenNoValido();
        }
    }

    @RequestMapping(value="/residencial/test_sending", method = RequestMethod.POST)
    def test_sending(@RequestBody Map  data ) {

        println(data);
        // JsonSlurper parser = new JsonSlurper()
        //Map MapData = parser.parseText(data);

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){

            MapData = MapData.data as Map

            print(MapData.idprovincia)
        }
        else{
            return MyCustomsRequests.TokenNoValido();
        }
    }

    @RequestMapping(value="/residencial/test_img", method = RequestMethod.POST)
    def test_img(@RequestBody Map  data ) {

        Map MapData = data
        //println(MapData);

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){

            MapData = MapData.data as Map
            String baseIMG = MapData.f_img;
//            println(baseIMG);
//            baseIMG = baseIMG //substring(0,baseIMG.indexOf(',',2))
//            println(baseIMG);
            byte[] baseIMGdecode = baseIMG.decodeBase64();
//            println(baseIMGdecode)

            return residencialservice.TestInsertIMG(baseIMGdecode)
        }
        else{
            return MyCustomsRequests.TokenNoValido();
        }
    }

    @RequestMapping(value="/residencial/get_test_img", method = RequestMethod.POST)
    def get_test_img(@RequestBody Map  data ) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){

            return residencialservice.GetImg(MapData.idIMG as int)
        }
        else{
            return MyCustomsRequests.TokenNoValido();
        }
    }

    @RequestMapping(value="/residencial/encryp", method = RequestMethod.POST)
    def getClientes(@RequestParam(name = "data", required = false) String data) {

        String rs = new String(data.decodeBase64())
        JsonSlurper parser = new JsonSlurper()
        Map MapData = parser.parseText(rs);

        if(MapData.key == "ApiRandiel2021"){
            MapData = MapData.data
            return residencialservice.GetNameId(MapData.MyId as int)
        }
        else{
            println("Token NO valido")
        }

    }


}
