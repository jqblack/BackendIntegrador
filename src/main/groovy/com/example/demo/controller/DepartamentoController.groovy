package com.example.demo.controller

import com.example.demo.Utilidades.CustomRequest
import com.example.demo.services.DepartamentoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class DepartamentoController {

    @Autowired
    DepartamentoService departamentoService

    CustomRequest MyCustomsRequests = new CustomRequest();


    @RequestMapping(value="/departamento/getdepartamentos", method = RequestMethod.POST)
    def GetDepartamentos(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){
            MapData = MapData.data;

            return departamentoService.GetDepartamentos(MapData.id as int)
        }
        else{
            return MyCustomsRequests.TokenNoValido();
        }
    }

    @RequestMapping(value="/departamento/insert", method = RequestMethod.POST)
    def InsertDepartamento(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){
            MapData = MapData.data;

            if(departamentoService.Insert(MapData.id as int,MapData.nombre as String,MapData.preventa as int, MapData.prealquiler as int,
                    MapData.dispoventa as Boolean,MapData.cantBath as int, MapData.cantHabi as int, MapData.amueblado as Boolean)){
                return MyCustomsRequests.MessageSuccess()
            }
            else{
                return MyCustomsRequests.MessageFailed()
            }
        }
        else{
            return MyCustomsRequests.TokenNoValido();
        }
    }

    @RequestMapping(value="/departamento/update", method = RequestMethod.POST)
    def UpdateDepartamento(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){
            MapData = MapData.data;

            if(departamentoService.Update(MapData.idDepa as int,MapData.nombre as String,MapData.preventa as int, MapData.prealquiler as int, MapData.dispoventa as Boolean)){
                return MyCustomsRequests.MessageSuccess()
            }
            else{
                return MyCustomsRequests.MessageFailed()
            }
        }
        else{
            return MyCustomsRequests.TokenNoValido();
        }
    }

    @RequestMapping(value="/departamento/listaInquilinos", method = RequestMethod.POST)
    def listaInquilinos(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){
            MapData = MapData.data;

            return departamentoService.listaInquilinos(MapData.idResi as int)
        }
        else{
            return MyCustomsRequests.TokenNoValido();
        }
    }

    @RequestMapping(value="/departamento/detallesdepartamento", method = RequestMethod.POST)
    def detallesdepartamento(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){
            MapData = MapData.data;

            return departamentoService.GetDetalles(MapData.idDeparmento as int)
        }
        else{
            return MyCustomsRequests.TokenNoValido();
        }
    }

    @RequestMapping(value="/departamento/serviciosdepartamento", method = RequestMethod.POST)
    def serviciosdepartamento(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){
            MapData = MapData.data;

            return departamentoService.ServiciosDepartamentos(MapData.idDeparmento as int)
        }
        else{
            return MyCustomsRequests.TokenNoValido();
        }
    }

    @RequestMapping(value="/departamento/insertserviciosDepartamento", method = RequestMethod.POST)
    def insertserviciosDepartamento(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){
            MapData = MapData.data;

            if(departamentoService.InsertServiciosDepartamentos(MapData.idServicio as int,MapData.idDepartamento as int)){
                return MyCustomsRequests.MessageSuccess()
            }
            else{
                return MyCustomsRequests.MessageFailed()
            }
        }
        else{
            return MyCustomsRequests.TokenNoValido();
        }
    }

    @RequestMapping(value="/departamento/getservicios", method = RequestMethod.POST)
    def getservicios(@RequestBody Map  data) {

        Map MapData = data
        println(data)
        if(MapData.key == "291290336b75b259b77e181c87cc974f"){
            MapData = MapData.data;

            return departamentoService.GetServicios(MapData.idResi as int)
        }
        else{
            return MyCustomsRequests.TokenNoValido();
        }
    }

    @RequestMapping(value="/departamento/getresidencial", method = RequestMethod.POST)
    def getresidencial(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){
            MapData = MapData.data;

            return departamentoService.getidResidencial(MapData.idDepa as int)
        }
        else{
            return MyCustomsRequests.TokenNoValido();
        }
    }

    @RequestMapping(value="/departamento/insertFotos", method = RequestMethod.POST)
    def insertFotos(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){
            MapData = MapData.data;

            if(departamentoService.InserImgDepartamento(MapData.idDepart as int,MapData.image as String)){
                return MyCustomsRequests.MessageSuccess()
            }
            else{
                return MyCustomsRequests.MessageFailed()
            }
        }
        else{
            return MyCustomsRequests.TokenNoValido();
        }
    }
}
