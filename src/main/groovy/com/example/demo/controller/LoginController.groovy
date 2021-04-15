package com.example.demo.controller

import com.example.demo.Utilidades.CustomRequest
import com.example.demo.services.LoginService
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class LoginController {

    @Autowired
    LoginService loginService

    CustomRequest MyCustomsRequests = new CustomRequest();

    @RequestMapping(value="/login/iniciarsesion", method = RequestMethod.POST)
    def Login(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){

            MapData = MapData.data;

            return loginService.VerifiedUser(MapData.username as String, MapData.pass as String)
        }
        else{
            return MyCustomsRequests.TokenNoValido();
        }
    }
}
