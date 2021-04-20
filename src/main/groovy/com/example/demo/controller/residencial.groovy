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

import java.text.SimpleDateFormat

@RestController
class residencial {

    @Autowired
    ResidencialService residencialservice

    CustomRequest MyCustomsRequests = new CustomRequest();


    @RequestMapping(value="/residencial/getAllResidencial", method = RequestMethod.POST)
    def getAllResidencial(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){
            residencialservice.getAllResidencial()
        }
        else{
            return MyCustomsRequests.TokenNoValido();
        }
    }

    @RequestMapping(value="/residencial/getresidenciales", method = RequestMethod.POST)
    def getresidencial(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){
            MapData = MapData.data;

            residencialservice.GetResidencialByOwner(MapData.idUser as int)
        }
        else{
            return MyCustomsRequests.TokenNoValido();
        }
    }

    @RequestMapping(value="/residencial/insert", method = RequestMethod.POST)
    def InsertResidencial(@RequestBody Map  data) {

        Map MapData = data
        println(MapData)

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){

            MapData = MapData.data;

            if(residencialservice.InsertResidencial(MapData.nombre as String,
                    MapData.provincia as int,
                    MapData.municipio as int,
                    MapData.sector as int,MapData.area as int,MapData.image as String, MapData.idUser as int)){

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
    def UpdateResidencial(@RequestBody Map  data) {

        Map MapData = (data);

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
    def DeleteResidencial(@RequestBody Map  data) {

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
    def OwnResidencial(@RequestBody Map  data) {

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
//            String baseIMG;
////            println(baseIMG);
////            baseIMG = baseIMG //substring(0,baseIMG.indexOf(',',2))
////            println(baseIMG);
//            byte[] baseIMGdecode = baseIMG.decodeBase64();
//            println(baseIMGdecode)

            return residencialservice.TestInsertIMG(MapData.f_img as String)
        }
        else{
            return MyCustomsRequests.TokenNoValido();
        }
    }


    @RequestMapping(value="/residencial/testget_img", method = RequestMethod.POST)
    def testget_img(@RequestBody Map  data ) {

        Map MapData = data
        //println(MapData);

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){

            MapData = MapData.data as Map


            return residencialservice.TestgetIMG(MapData.IDimg as int)
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

    @RequestMapping(value="/residencial/getservicios", method = RequestMethod.POST)
    def getservicios(@RequestBody Map  data ) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){

            MapData = MapData.data

            return residencialservice.GetServicos(MapData.idResi as int)
        }
        else{
            return MyCustomsRequests.TokenNoValido();
        }
    }

    @RequestMapping(value="/residencial/insertpredeterminados", method = RequestMethod.POST)
    def insertpredeterminados(@RequestBody Map  data) {

        Map MapData  = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){

            MapData = MapData.data;

            if(residencialservice.InsertPredeterminadoServices(
                    MapData.idResi as int,
                    MapData.tipo as String,
                    MapData.list as List)){

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

    @RequestMapping(value="/residencial/getserviciospredeterminados", method = RequestMethod.POST)
    def getserviciospredeterminados(@RequestBody Map  data ) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){

            MapData = MapData.data

            return residencialservice.MisServiciosPredeterminados(MapData.idResi as int)
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



    @RequestMapping(value="/residencial/testinsertaxios", method = RequestMethod.POST)
    def residencial_testinsertaxios(@RequestBody Map  data) {

        println(data);
        Map MapData = data;

        println(MapData)

//        if(MapData.key == "291290336b75b259b77e181c87cc974f"){
//            return residencialservice.Get_Provincias()
//        }
//        else{
//            return MyCustomsRequests.TokenNoValido();
//        }
    }

    @RequestMapping(value="/residencial/testdate", method = RequestMethod.POST)
    def testdate(@RequestBody Map  data) {

        Map MapData = data;


        if(MapData.key == "291290336b75b259b77e181c87cc974f"){
            MapData = MapData.data;

            SimpleDateFormat sdformat = new SimpleDateFormat("dd/MM/yyyy");
            Date dt = sdformat.parse(MapData.fecha as String);
            println (dt)

            return residencialservice.TestDate(dt)
        }
        else{
            return MyCustomsRequests.TokenNoValido();
        }
    }

    @RequestMapping(value="/residencial/testgetaxios", method = RequestMethod.POST)
    def residencial_testgetaxios() {

        return residencialservice.getDataTest()
    }

}
