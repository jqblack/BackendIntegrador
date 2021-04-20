package com.example.demo.controller

import com.example.demo.Utilidades.CustomRequest
import com.example.demo.services.ComplementosService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

import java.text.SimpleDateFormat

@RestController
class ComplementosController {

    CustomRequest MyCustomsRequests = new CustomRequest();

    @Autowired
    ComplementosService complementosService

    @RequestMapping(value="/complementos/get_provincias", method = RequestMethod.POST)
    def Get_Provincias(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){
            return complementosService.Get_Provincias()
        }
        else{
            return MyCustomsRequests.TokenNoValido();
        }
    }

    @RequestMapping(value="/complementos/get_municipio", method = RequestMethod.POST)
    def get_municipio(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){
            return complementosService.Get_Municipio()
        }
        else{
            return MyCustomsRequests.TokenNoValido();
        }
    }

    @RequestMapping(value="/complementos/get_sector", method = RequestMethod.POST)
    def get_sector(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){
            return complementosService.Get_Sector()
        }
        else{
            return MyCustomsRequests.TokenNoValido();
        }
    }

    @RequestMapping(value="/complementos/test", method = RequestMethod.GET)
    def test() {

        Map mapa = [:]
        if(mapa == [:]){
            return "Randiel"
        }else{
            return "test"
        }

    }

    @RequestMapping(value="/complementos/calificar", method = RequestMethod.POST)
    def calificar(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){

            MapData = MapData.data;

            if(complementosService.InsertClasificacion(MapData.idUser as int, MapData.idDepartamento as int, MapData.calificacion as int, MapData.descripcion as String)){

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


    @RequestMapping(value="/complementos/listasolicitud", method = RequestMethod.POST)
    def listasolicitud(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){

            MapData = MapData.data;

            return complementosService.listaSolicitudes(MapData.idUser as int)
        }
        else{
            return MyCustomsRequests.TokenNoValido();
        }
    }

    @RequestMapping(value="/complementos/insertsolicitud", method = RequestMethod.POST)
    def InsertSolicitud(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){

            MapData = MapData.data;
            println(data)

            Boolean isCompra;
            if(MapData.iscompra as int == 2){
                isCompra = true;
            }
            else{
                isCompra = false
            }

            if(complementosService.InsertSolicitud(MapData.idUser as int, MapData.idDepar as int, MapData.idResi as int, isCompra)){

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

    @RequestMapping(value="/complementos/getempleados", method = RequestMethod.POST)
    def getempleados(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){

            MapData = MapData.data;

            return complementosService.ListaEmpleado(MapData.idResi as int)
        }
        else{
            return MyCustomsRequests.TokenNoValido();
        }
    }

    @RequestMapping(value="/complementos/getsolicitudesempleados", method = RequestMethod.POST)
    def getsolicitudesempleados(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){

            MapData = MapData.data;

            return complementosService.getSolicitudesEmpleados(MapData.idUser as int)
        }
        else{
            return MyCustomsRequests.TokenNoValido();
        }
    }

    @RequestMapping(value="/complementos/convertirempleado", method = RequestMethod.POST)
    def convertirempleado(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){
            println(data)
            MapData = MapData.data;

            if(complementosService.cambiarEmpleado(MapData.idUser as int, MapData.idResi as int)){

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

    @RequestMapping(value="/complementos/rechazarempleado", method = RequestMethod.POST)
    def rechazarempleado(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){
            println(data)
            MapData = MapData.data;

            if(complementosService.rechazarempleado(MapData.idUser as int, MapData.idResi as int)){

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

    @RequestMapping(value="/complementos/insertsolicitudempleado", method = RequestMethod.POST)
    def insertsolicitudempleado(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){

            MapData = MapData.data;
            println(data)

            if(complementosService.InsertSolicitudEmpleados(MapData.idUser as int, MapData.idResi as int)){

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

    @RequestMapping(value="/complementos/tareaspendientes", method = RequestMethod.POST)
    def tareaspendientes(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){

            MapData = MapData.data;

            return complementosService.TareasPendientes(MapData.idUser as int)
        }
        else{
            return MyCustomsRequests.TokenNoValido();
        }
    }

    @RequestMapping(value="/complementos/insertartarea", method = RequestMethod.POST)
    def insertartarea(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){

            MapData = MapData.data;
            SimpleDateFormat sdformat = new SimpleDateFormat("dd/MM/yyyy");
            Date dt = sdformat.parse(MapData.fecha as String);
            println (dt)


            if(complementosService.InsertTarea(MapData.idArea as int, MapData.idUser as int, MapData.idMant as int, dt)){

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

    @RequestMapping(value="/complementos/insertinquilinos", method = RequestMethod.POST)
    def insertinquilinos(@RequestBody Map  data) {

        Map MapData = data

        if(MapData.key == "291290336b75b259b77e181c87cc974f"){

            MapData = MapData.data;
            println(data)
            if(complementosService.InsertInquilino(
                    MapData.idUser as int,
                    MapData.idDepart as int,
                    MapData.nomDepartameto as String,
                    MapData.idResi as int,
                    MapData.idPerso as int)){

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


//    @ConditionalOnProperty(name = "scheduling.enabled", matchIfMissing = true)
//
//    @Scheduled(initialDelay = 1000L, fixedDelayString = "PT5S")
//    public void TareaProgramada() throws InterruptedException{
//
//        ComplementosService comple = new ComplementosService()
//
//        comple.ExecuteTrigger()
//    }

}
