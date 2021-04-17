package com.example.demo.services

import com.example.demo.database.Sql
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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

    List TareasPendientes(){
        String query = ""

        return sql.executeQueryAsList(query)
    }
}
