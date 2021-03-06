package com.example.demo.controller

import com.example.demo.Utilidades.CustomRequest
import com.example.demo.services.UsuarioService
import com.sun.javafx.collections.MappingChange
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
        println(data);

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){

            MapData = MapData.data;

            if(usuarioService.InsertUser(MapData.nombre as String,MapData.apellido as String,MapData.sexo as int, MapData.user as String,
                        MapData.pass as String ,MapData.numCuenta as String, MapData.tipouser as Boolean, MapData.cel as String)){
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

    @RequestMapping(value="/usuario/cuentaxcobrar", method = RequestMethod.POST)
    def cuentaxcobrar(@RequestBody Map  data) {

        Map MapData = data
        println(data);

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){

            MapData = MapData.data;

            return usuarioService.GetCuentaCobrar(MapData.idUser as int)
        }
        else{
            return MyCustomsRequests.TokenNoValido();
        }
    }

    @RequestMapping(value="/usuario/simulacionpago", method = RequestMethod.POST)
    def simulacionpago(@RequestBody Map  data) {

        Map MapData = data
        println(data);

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){

            MapData = MapData.data;

            if(usuarioService.simulacionpago(MapData.idUser as int)){
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

    @RequestMapping(value="/usuario/getidresidencial", method = RequestMethod.POST)
    def getidresidencial(@RequestBody Map  data) {

        Map MapData = data
        println(data);

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){

            MapData = MapData.data;

            return usuarioService.getResidencial(MapData.idUser as int)
        }
        else{
            return MyCustomsRequests.TokenNoValido();
        }
    }

}
