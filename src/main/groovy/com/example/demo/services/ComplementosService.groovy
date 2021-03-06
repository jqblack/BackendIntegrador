package com.example.demo.services

import com.example.demo.database.Sql
import org.apache.tomcat.util.descriptor.web.Injectable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Configuration
@EnableScheduling
@Service
@Transactional
@ConditionalOnProperty(name = "scheduling.enabled", matchIfMissing = true)
class Test{
    @Autowired
    Sql sql

    @Scheduled(initialDelay = 1000L, fixedDelayString = "PT10H")
    public void TriggerCuentasPorCobrar() throws InterruptedException{

        String query = " SELECT \n" +
                " *\n" +
                " FROM PUBLIC.\"Departamentos\" AS D\n" +
                " WHERE D.\"Disponible\" = FALSE";

        List lisaDepartamentos = sql.executeQueryAsList(query)

        if(lisaDepartamentos.size() > 0){

            for (int i = 0; i < lisaDepartamentos.size(); i++) {

                query = " SELECT \n" +
                        " *\n" +
                        " FROM PUBLIC.\"DepartamentoVsServicos\" AS DS\n" +
                        " WHERE DS.\"ID_Departamento\" = ${lisaDepartamentos[i].ID_departamento}"

                List serviciosDepartamentos = sql.executeQueryAsList(query)

                query = " SELECT \n" +
                        "  \"ID_usuario\"\n" +
                        "FROM \n" +
                        "  public.\"Inquilino\" AS I\n" +
                        "  WHERE I.\"ID_deparamento\" = ${lisaDepartamentos[i].ID_departamento}"


                Map mapaUser = sql.executeQueryAsMap(query)

                for (int j = 0; j < serviciosDepartamentos.size(); j++) {

                    query = "SELECT \n" +
                            "  \"ID_servicio\",\n" +
                            "  \"Descripcion\",\n" +
                            "  cobro,\n" +
                            "  pago,\n" +
                            "  \"idResidencial\"\n" +
                            "FROM \n" +
                            "  public.\"Servicios\" AS S \n" +
                            "  WHERE S.\"ID_servicio\" = ${serviciosDepartamentos[j].ID_servicio}"

                    Map mapaServicio = sql.executeQueryAsMap(query)


                    query = "  INSERT INTO \n" +
                            "  public.\"CuentaPorCobrar\"\n" +
                            "(\n" +
                            "  \"Idusuario\",\n" +
                            "  \"IdReferencia\",\n" +
                            "  \"IdTipoCuentaxCobrar\",\n" +
                            "  monto\n" +
                            ")\n" +
                            "VALUES (\n" +
                            "  ${mapaUser.ID_usuario},\n" +
                            "  ${mapaServicio.ID_servicio},\n" +
                            "  ${1},\n" +
                            "  ${mapaServicio.cobro}\n" +
                            ");"

                    sql.executeQueryInsertUpdate(query)
                }

                //println("Residencial "+i+1+" done")
            }

        }


    }

    @Scheduled(initialDelay = 1000L, fixedDelayString = "PT24H")
    public void TriggerAreasComunes() throws InterruptedException{

        String query = "SELECT \n" +
                "  AM.*\n" +
                "FROM \n" +
                "  public.\"AreasComunesVsMantenimientos\" AM\n" +
                "  WHERE AM.\"fechaProgramada\" = current_date"

        List listaAreasToday = sql.executeQueryAsList(query)

        if(listaAreasToday.size() > 0){

            for (int i = 0; i < listaAreasToday.size(); i++) {

                query = "  INSERT INTO \n" +
                        "  public.\"AreaComunpendientesbyUsuarios\"\n" +
                        "(\n" +
                        "  id_areacomun,\n" +
                        "  \"idUsuario\",\n" +
                        "  \"idMantenimiento\"\n" +
                        ")\n" +
                        "VALUES (\n" +
                        "  ${listaAreasToday[i].id_areacomun},\n" +
                        "  ${listaAreasToday[i].idUsuarioDefault},\n" +
                        "  ${listaAreasToday[i].id_TipoMantenimiento}\n" +
                        ");"

                sql.executeQueryInsertUpdate(query)

                query = "SELECT \n" +
                        "  M.*\n" +
                        "FROM \n" +
                        "  public.\"MantenimientoArea\" AS M\n" +
                        "  WHERE M.\"ID_TipoMantenimiento\" = ${listaAreasToday[i].id_TipoMantenimiento}"

                Map mapaMantenimiento = sql.executeQueryAsMap(query)

                query = "UPDATE \n" +
                        "  public.\"AreasComunesVsMantenimientos\" \n" +
                        "SET \n" +
                        "  \"fechaProgramada\" = current_date+${mapaMantenimiento.cantidadDias}\n" +
                        "WHERE \n" +
                        " id_areacomun = ${listaAreasToday[i].id_areacomun} AND \n" +
                        "  \"id_TipoMantenimiento\" = ${listaAreasToday[i].id_TipoMantenimiento} AND \n" +
                        "  \"idUsuarioDefault\" = ${listaAreasToday[i].idUsuarioDefault}\n" +
                        ";"

                sql.executeQueryInsertUpdate(query)
                //println("Cambie")

            }
        }


    }


}


@Service
@Transactional
class ComplementosService {

    @Autowired
    Sql sql


    List Get_Provincias(){

        String query = "SELECT \n" +
                "  \"ID_provincia\" as value,\n" +
                "  descripcion as label\n" +
                "FROM \n" +
                "  public.\"Provincia\" ;"

        return sql.executeQueryAsList(query);
    }

     public void ExecuteTrigger(){

        Sql sql2 = new Sql()

        String query = " SELECT \n" +
                " *\n" +
                " FROM PUBLIC.\"Departamentos\" AS D\n" +
                " WHERE D.\"Disponible\" = TRUE";
        
        List lisaDepartamentos = sql2.executeQueryAsList(query)
        
        if(lisaDepartamentos.size() > 0){

            for (int i = 0; i < lisaDepartamentos.size(); i++) {

                query = " SELECT \n" +
                        " *\n" +
                        " FROM PUBLIC.\"DepartamentoVsServicos\" AS DS\n" +
                        " WHERE DS.\"ID_Departamento\" = ${lisaDepartamentos[i].ID_departamento}"

                List serviciosDepartamentos = sql.executeQueryAsList(query)

                query = " SELECT \n" +
                        "  UD.*\n" +
                        "FROM \n" +
                        "  public.\"UsuarioVsDepartamento\" AS UD\n" +
                        "  WHERE UD.\"idDepartamento\" = ${lisaDepartamentos[i].ID_departamento}"

                Map mapaUser = sql.executeQueryAsMap(query)

                for (int j = 0; j < serviciosDepartamentos.size(); j++) {

                    query = "SELECT \n" +
                            "  \"ID_servicio\",\n" +
                            "  \"Descripcion\",\n" +
                            "  cobro,\n" +
                            "  pago,\n" +
                            "  \"idResidencial\"\n" +
                            "FROM \n" +
                            "  public.\"Servicios\" AS S \n" +
                            "  WHERE S.\"ID_servicio\" = ${serviciosDepartamentos[j].ID_servicio}"

                    Map mapaServicio = sql.executeQueryAsMap(query)


                    query = "  INSERT INTO \n" +
                            "  public.\"CuentaPorCobrar\"\n" +
                            "(\n" +
                            "  \"Idusuario\",\n" +
                            "  \"IdReferencia\",\n" +
                            "  \"IdTipoCuentaxCobrar\",\n" +
                            "  monto\n" +
                            ")\n" +
                            "VALUES (\n" +
                            "  ${mapaUser.idUser},\n" +
                            "  ${mapaServicio.ID_servicio},\n" +
                            "  ${1},\n" +
                            "  ${mapaServicio.cobro}\n" +
                            ");"

                    sql.executeQueryInsertUpdate(query)
                }

            }

        }

    }

    List Get_Municipio(){

        String query = "  SELECT \n" +
                "  \"ID_municipio\" as value,\n" +
                "  \"ID_Provincia\" as idprovincia,\n" +
                "  descripcion as label\n" +
                "FROM \n" +
                "  public.\"Municipio\" ;"

        return sql.executeQueryAsList(query);
    }

    List Get_Sector(){

        String query = "SELECT \n" +
                "  \"ID_sector\" as value,\n" +
                "  \"ID_Municipio\" as idmunicipio,\n" +
                "  descripcion as label\n" +
                "FROM \n" +
                "  public.\"Sector\" ;"

        return sql.executeQueryAsList(query);
    }

    Boolean InsertClasificacion(int idUser, int cali, String des){
        String query = "  SELECT \n" +
                "  I.\"ID_deparamento\"\n" +
                "FROM \n" +
                "  public.\"Inquilino\" AS I\n" +
                "  WHERE I.\"ID_usuario\" = ${idUser}"

        Map mapaUser = sql.executeQueryAsMap(query)

        query = "INSERT INTO \n" +
                "  public.\"Calificacion\"\n" +
                "(\n" +
                "  \"ID_usuario\",\n" +
                "  \"ID_departamento\",\n" +
                "  \"Calificacion\",\n" +
                "  descripcion,\n" +
                "  fecha\n" +
                ")\n" +
                "VALUES (\n" +
                "  ${idUser},\n" +
                "  ${mapaUser.ID_deparamento},\n" +
                "  ${cali},\n" +
                "  '${des}',\n" +
                "  now()\n" +
                ");"

        return sql.executeQueryInsertUpdate(query)
    }

    List listaSolicitudes(int idUser){

        String query = "SELECT \n" +
                "  RO.\"Id_residencial\" AS id\n" +
                "FROM \n" +
                "  public.\"OwnersVsResidencia\" RO\n" +
                "  WHERE RO.\"ID_usuario\" = ${idUser}"

        List listaresi = sql.executeQueryAsList(query);
        List Data =[]

        for (int i = 0; i < listaresi.size(); i++) {

            query = "SELECT \n" +
                    "SC.*,\n" +
                    "D.\"Nombre_departamento\", P.*, \n" +
                    "CONCAT(P.\"Nombre\",' ',P.\"Apellido\") AS nombrePersona \n" +
                    "FROM PUBLIC.\"SolicitudCompra\" AS SC\n" +
                    "INNER JOIN PUBLIC.\"Departamentos\" AS D\n" +
                    "ON SC.\"ID_departamento\" = D.\"ID_departamento\"\n" +
                    "INNER JOIN PUBLIC.\"Usuario\" AS U\n" +
                    "ON SC.\"ID_usuario\" = U.\"idUsuario\"\n" +
                    "INNER JOIN PUBLIC.\"Persona\" AS P \n" +
                    "ON U.\"IdPersona\" = P.\"IdPersona\"\n" +
                    "WHERE SC.\"Activo\" = TRUE AND SC.\"idResidencial\" = ${listaresi[i].id}"

            Data += sql.executeQueryAsList(query)

        }


        return Data
    }

    List GetPlanesResidencial(int idResi){

        String query = "SELECT \n" +
                "  numserial AS value,\n" +
                "  tipo AS label\n" +
                "FROM \n" +
                "  public.\"TipoPredeterminadoserivios\" AS TP\n" +
                "  WHERE TP.\"idResidencial\" = ${idResi}"

        return sql.executeQueryAsList(query)

    }

    Boolean InsertSolicitud(int idUser, int idDepart, int idResi, Boolean isCompra){
                String query = "INSERT INTO \n" +
                "  public.\"SolicitudCompra\"\n" +
                "(\n" +
                "  \"ID_usuario\",\n" +
                "  \"ID_departamento\",\n" +
                "  \"idResidencial\",\n" +
                "  \"isCompra\"\n" +
                ")\n" +
                "VALUES (\n" +
                "  ${idUser},\n" +
                "  ${idDepart},\n" +
                "  ${idResi},\n" +
                "  ${isCompra}\n" +
                ");"

        return sql.executeQueryInsertUpdate(query)
    }

    List ListaEmpleado(int idResi){
        String query = "SELECT \n" +
                "  ER.*,\n" +
                "  CONCAT(P.\"Nombre\",' ',P.\"Apellido\") AS nombrePersona \n" +
                "FROM \n" +
                "  public.\"EmpleadosvsResidencial\"  as ER\n" +
                "  INNER JOIN PUBLIC.\"Usuario\" AS U\n" +
                "  ON ER.\"idUsuario\" = U.\"idUsuario\"\n" +
                "  INNER JOIN PUBLIC.\"Persona\" AS P\n" +
                "  ON U.\"IdPersona\" = P.\"IdPersona\"\n" +
                "  WHERE ER.activo = TRUE AND ER.\"idResidencial\" = ${idResi}"

        return sql.executeQueryInsertUpdate(query)
    }

    List getSolicitudesEmpleados(int idUser){
        String query = "SELECT \n" +
                "  RO.\"Id_residencial\" AS id\n" +
                "FROM \n" +
                "  public.\"OwnersVsResidencia\" RO\n" +
                "  WHERE RO.\"ID_usuario\" = ${idUser}"

        List listaresi = sql.executeQueryAsList(query);
        List Datos = []

        for (int i = 0; i < listaresi.size(); i++) {
            query = "  SELECT \n" +
                    "  SE.*, R.nombre AS nombreresi, \n" +
                    "  CONCAT(P.\"Nombre\",' ',P.\"Apellido\") AS nombre, P.celular \n" +
                    "  FROM \n" +
                    "  PUBLIC.\"SolicitudEmpleados\" AS SE\n" +
                    "  INNER JOIN PUBLIC.\"Usuario\" AS U\n" +
                    "  ON SE.\"idUser\" = U.\"idUsuario\" " +
                    "INNER JOIN PUBLIC.\"Persona\" AS P " +
                    "ON U.\"IdPersona\" = P.\"IdPersona\"  " +
                    "INNER JOIN PUBLIC.\"Residencial\" AS R " +
                    "ON R.\"ID_residencial\" = SE.\"idResidencial\" \n" +
                    "  WHERE SE.activo = TRUE AND SE.\"idResidencial\" = ${listaresi[i].id}"

            Datos += sql.executeQueryAsList(query)
        }

        return Datos
    }

    Boolean cambiarEmpleado(int idUser, int idResi){
        String query = "INSERT INTO \n" +
                "  public.\"EmpleadosvsResidencial\"\n" +
                "(\n" +
                "  \"idUsuario\",\n" +
                "  \"idResidencial\"\n" +
                ")\n" +
                "VALUES (\n" +
                "  ${idUser},\n" +
                "  ${idResi}\n" +
                ");"

             if(sql.executeQueryInsertUpdate(query)){

                 query = "UPDATE \n" +
                         "  public.\"SolicitudEmpleados\" \n" +
                         "SET \n" +
                         "  activo = FALSE\n" +
                         "WHERE \n" +
                         "\"idUser\" = ${idUser} AND \n" +
                         "  \"idResidencial\" = ${idResi}\n" +
                         ";"

                 return sql.executeQueryInsertUpdate(query)
             }
        else{
                 return false
             }

    }

    Boolean rechazarempleado(int idUser, int idResi){
       String  query = "UPDATE \n" +
               "  public.\"SolicitudEmpleados\" \n" +
               "SET \n" +
               "  activo = FALSE\n" +
               "WHERE \n" +
               "\"idUser\" = ${idUser} AND\n" +
               "  \"idResidencial\" = ${idResi}\n" +
               ";"

        return sql.executeQueryInsertUpdate(query)
    }

    Boolean InsertSolicitudEmpleados(int idUser, int idResi){
        String query = "INSERT INTO \n" +
                "  public.\"SolicitudEmpleados\"\n" +
                "(\n" +
                "  \"idUser\",\n" +
                "  \"idResidencial\"\n" +
                ")\n" +
                "VALUES (\n" +
                "  ${idUser},\n" +
                "  ${idResi}\n" +
                ");"
println(query)
        return sql.executeQueryInsertUpdate(query)
    }

    List TareasPendientes(int idUser){
        String query = "SELECT \n" +
                "  AC.*,\n" +
                "  M.\"Descripcion\" AS mantenimiento\n" +
                "FROM \n" +
                "  public.\"AreaComunpendientesbyUsuarios\" AS AC\n" +
                "  INNER JOIN PUBLIC.\"MantenimientoArea\" AS M \n" +
                "  ON AC.\"idMantenimiento\" = M.\"ID_TipoMantenimiento\"\n" +
                "  WHERE AC.activo = TRUE AND AC.\"idUsuario\" = ${idUser}"

        return sql.executeQueryAsList(query)
    }

    Boolean InsertTarea(int idArea, int idUser, int idMant, Date fecha){
        String query = "INSERT INTO \n" +
                "  public.\"AreaComunpendientesbyUsuarios\"\n" +
                "(\n" +
                "  id_areacomun,\n" +
                "  \"idUsuario\",\n" +
                "  \"idMantenimiento\",\n" +
                "  \"fechaMantenimiento\"\n" +
                ")\n" +
                "VALUES (\n" +
                "  ${idArea},\n" +
                "  ${idUser},\n" +
                "  ${idMant},\n" +
                "  to_date(to_char('${fecha}'::DATE,'dd/mm/yyyy'),'dd/mm/yyyy')\n" +
                ");  "

        return sql.executeQueryInsertUpdate(query)
    }

    Boolean InsertInquilino(int idUser, int idDepart, String nomDepart, int idResi, int idPerso, int idPlan){
        String query = "INSERT INTO \n" +
                "  public.\"Inquilino\"\n" +
                "(\n" +
                "  \"ID_usuario\",\n" +
                "  \"ID_deparamento\",\n" +
                "  \"Nombre_departamento\",\n" +
                "  \"idResidencial\",\n" +
                "  \"idPersona\"\n" +
                ")\n" +
                "VALUES (\n" +
                "  ${idUser},\n" +
                "  ${idDepart},\n" +
                "  '${nomDepart}',\n" +
                "  ${idResi},\n" +
                "  ${idPerso}\n" +
                ");"

        if(sql.executeQueryInsertUpdate(query)){

            query = "UPDATE \n" +
                    "  public.\"SolicitudCompra\" \n" +
                    "SET \n" +
                    "  \"Activo\" = FALSE\n" +
                    "WHERE \n" +
                    "\"ID_usuario\" = ${idUser} AND\n" +
                    "  \"ID_departamento\" = ${idDepart} AND\n" +
                    "  \"idResidencial\" = ${idResi}\n" +
                    ";"

            sql.executeQueryInsertUpdate(query)

            query = "SELECT \n" +
                    "  \"idTipopredeterminado\",\n" +
                    "  \"idServicio\"\n" +
                    "FROM \n" +
                    "  public.\"ServiciosPredeterminados\" AS SP\n" +
                    "  WHERE SP.\"idTipopredeterminado\" = ${idPlan}"

            List listaservices = []

            listaservices = sql.executeQueryAsList(query)

            for (int i = 0; i < listaservices.size(); i++) {
                query = "  INSERT INTO \n" +
                        "  public.\"DepartamentoVsServicos\"\n" +
                        "(\n" +
                        "  \"ID_servicio\",\n" +
                        "  \"ID_Departamento\"\n" +
                        ")\n" +
                        "VALUES (\n" +
                        "  ${listaservices[i].idServicio}, \n" +
                        "  ${idDepart}\n" +
                        ");"
                sql.executeQueryInsertUpdate(query)
            }

            return true
        }
        else{
            return false
        }

    }
}
