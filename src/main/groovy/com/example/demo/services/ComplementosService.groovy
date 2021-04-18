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

    @Scheduled(initialDelay = 1000L, fixedDelayString = "PT15H")
    public void TriggerCuentasPorCobrar() throws InterruptedException{

        String query = " SELECT \n" +
                " *\n" +
                " FROM PUBLIC.\"Departamentos\" AS D\n" +
                " WHERE D.\"Disponible\" = TRUE";

        List lisaDepartamentos = sql.executeQueryAsList(query)

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

                println("Residencial "+i+1+" done")
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

    Boolean InsertClasificacion(int idUser, int idDepart, int cali, String des){
        String query = "INSERT INTO \n" +
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
                "  ${idDepart},\n" +
                "  ${cali},\n" +
                "  '${des}',\n" +
                "  now()\n" +
                ");"

        return sql.executeQueryInsertUpdate(query)
    }

    List listaSolicitudes(int idResi){
        String query = "SELECT \n" +
                "SC.*,\n" +
                "D.\"Nombre_departamento\",\n" +
                "CONCAT(P.\"Nombre\",' ',P.\"Apellido\") AS nombrePersona \n" +
                "FROM PUBLIC.\"SolicitudCompra\" AS SC\n" +
                "INNER JOIN PUBLIC.\"Departamentos\" AS D\n" +
                "ON SC.\"ID_departamento\" = D.\"ID_departamento\"\n" +
                "INNER JOIN PUBLIC.\"Usuario\" AS U\n" +
                "ON SC.\"ID_usuario\" = U.\"idUsuario\"\n" +
                "INNER JOIN PUBLIC.\"Persona\" AS P \n" +
                "ON U.\"IdPersona\" = P.\"IdPersona\"\n" +
                "WHERE SC.\"Activo\" = TRUE AND SC.\"idResidencial\" = ${idResi}"

        return sql.executeQueryAsList(query)
    }

    Boolean InsertSolicitud(int idUser, int idDepart, int idResi, Boolean isCompra){
                query = "INSERT INTO \n" +
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

    List getSolicitudesEmpleados(int idResi){
        String query = "  SELECT \n" +
                "  SE.*,\n" +
                "  CONCAT(P.\"Nombre\",' ',P.\"Apellido\") AS nombrePersona \n" +
                "  FROM \n" +
                "  PUBLIC.\"SolicitudEmpleados\" AS SE\n" +
                "  INNER JOIN PUBLIC.\"Persona\" AS P\n" +
                "  ON SE.\"idPersona\" = P.\"IdPersona\"\n" +
                "  WHERE SE.activo = TRUE AND SE.\"idResidencial\" = ${idResi}"

        return sql.executeQueryAsList(query);
    }

    Boolean InsertSolicitudEmpleados(int idUser, int idResi, int idPerso){
        String query = "INSERT INTO \n" +
                "  public.\"SolicitudEmpleados\"\n" +
                "(\n" +
                "  \"idUser\",\n" +
                "  \"idResidencial\",\n" +
                "  \"idPersona\"\n" +
                ")\n" +
                "VALUES (\n" +
                "  ${idUser},\n" +
                "  ${idResi},\n" +
                "  ${idPerso}\n" +
                ");"

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
}
