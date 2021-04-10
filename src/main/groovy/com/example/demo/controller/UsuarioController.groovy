package com.example.demo.controller

import com.example.demo.Utilidades.CustomRequest
import com.example.demo.services.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class UsuarioController {

    @Autowired
    UsuarioService usuarioService

    CustomRequest MyCustomsRequests = new CustomRequest();

    @RequestMapping(value="/usuario/insert", method = RequestMethod.POST)
    def insertUser(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){

            MapData = MapData.data;

            if(usuarioService.InsertUser(MapData.nombre as String,MapData.apellido as String,MapData.sexo as int, MapData.user as String,MapData.pass as String,MapData.numCuenta as String)){
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

    @RequestMapping(value="/usuario/login", method = RequestMethod.POST)
    def getAllResidencial(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){

            MapData = MapData.data;
            Map mapaUser = [:]
            Map Myreturn = [:]


            mapaUser = usuarioService.Login(MapData.user as String, MapData.pass as String)

            if(mapaUser != [:]){
                Myreturn.put("datosUser",mapaUser);
                Myreturn.put("permisoslist",usuarioService.GetPermissionUser(mapaUser.idUsuario as int))

                return Myreturn;
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
