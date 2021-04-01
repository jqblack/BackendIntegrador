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

    CustomRequest customRequest

    @RequestMapping(value="/residencial/lista", method = RequestMethod.POST)
    def GetListResidencial(@RequestBody String  data) {
       println(data)
        println("hola")
//        JsonSlurper parser = new JsonSlurper()
//        Map MapData = parser.parseText(rs);
//        println(MapData)
        return residencialservice.getResidencialList();
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
