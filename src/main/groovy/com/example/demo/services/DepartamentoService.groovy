package com.example.demo.services

import com.example.demo.database.Sql
import com.sun.org.apache.xpath.internal.operations.Bool
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class DepartamentoService {

    @Autowired
    Sql sql

    List GetDepartamentos(int idTorre){
        String query = "SELECT \n" +
                "  \"ID_departamento\" as id,\n" +
                "  \"ID_torre\",\n" +
                "  \"Nombre_departamento\",\n" +
                "  \"Disponible\",\n" +
                "  \"PrecioVenta\",\n" +
                "  \"PrecioAlquiler\",\n" +
                "  \"VentaDisponible\", image\n" +
                "FROM \n" +
                "  public.\"Departamentos\" WHERE \"ID_torre\" = ${idTorre}"

        return sql.executeQueryAsList(query)
    }

    Boolean Insert(int idTorre, String nom, int venta, int alquiler, Boolean disponibleVenta, int cantBath, int cantHabi, Boolean amueblado, String image){
        String query = "INSERT INTO \n" +
                "  public.\"Departamentos\"\n" +
                "(\n" +
               "\"ID_torre\",\n" +
                "  \"Nombre_departamento\",\n" +
                "  \"Disponible\",\n" +
                "  \"PrecioVenta\",\n" +
                "  \"PrecioAlquiler\",\n" +
                "  \"VentaDisponible\",\n" +
                "  \"cantidadBath\",\n" +
                "  canthabitaciones,\n" +
                "  \"isAmueblado\", image \n" +
                ")\n" +
                "VALUES (\n" +
                "  ${idTorre},\n" +
                "  '${nom}',\n" +
                "  true,\n" +
                "  ${venta},\n" +
                "  ${alquiler},\n" +
                "  ${disponibleVenta},\n" +
                        "  ${cantBath},\n" +
                        "  ${cantHabi},\n" +
                        "  ${amueblado} , '${image}'\n" +
                "); ";

        return sql.executeQueryInsertUpdate(query)
    }

    Boolean Update(int idDepa, String nom, int venta, int alquiler, Boolean disponibleVenta){

        String query = "UPDATE \n" +
                "  public.\"Departamentos\" \n" +
                "SET \n" +
                "  \"Nombre_departamento\" = '${nom}',\n" +
                "  \"Disponible\" = true,\n" +
                "  \"PrecioVenta\" = ${venta},\n" +
                "  \"PrecioAlquiler\" = ${alquiler},\n" +
                "  \"VentaDisponible\" = ${disponibleVenta}\n" +
                "WHERE \n" +
                "  \"ID_departamento\" = ${idDepa}\n" +
                ";";

        return sql.executeQueryInsertUpdate(query)
    }

    List listaInquilinos(int idResi){
//        String query = "SELECT \n" +
//                "I.*,\n" +
//                "D.\"Nombre_departamento\",\n" +
//                "CONCAT(P.\"Nombre\",' ',P.\"Apellido\") AS nombrePersona \n" +
//                "FROM PUBLIC.\"Inquilino\" AS I \n" +
//                "INNER JOIN PUBLIC.\"Departamentos\" AS D\n" +
//                "ON I.\"ID_deparamento\" = D.\"ID_departamento\"\n" +
//                "INNER JOIN public.\"Persona\" AS P\n" +
//                "ON I.\"idPersona\" = P.\"IdPersona\"\n" +
//                "WHERE I.\"idResidencial\" = ${idResi}"

        String query = " SELECT \n" +
                "  I.\"ID_usuario\" AS value,\n" +
                "  I.\"Nombre_departamento\" AS label\n" +
                "FROM \n" +
                "  public.\"Inquilino\"  AS I\n" +
                "  WHERE I.\"idResidencial\" = ${idResi}"

        return sql.executeQueryAsList(query)
    }

    Map GetDetalles(int id){
        String query = "SELECT \n" +
                "  D.*\n" +
                "FROM \n" +
                "  public.\"Departamentos\" AS D \n" +
                "  WHERE D.\"ID_departamento\" = ${id}"

        return sql.executeQueryAsMap(query)
    }

    List ServiciosDepartamentos(int idDepart){
        String query = "SELECT \n" +
                "  DS.\"ID_servicio\",\n" +
                "  DS.\"ID_Departamento\", S.\"Descripcion\" AS nombre\n" +
                "FROM \n" +
                "  public.\"DepartamentoVsServicos\"  AS DS INNER JOIN PUBLIC.\"Servicios\" AS S ON DS.\"ID_servicio\" = S.\"ID_servicio\" \n" +
                "  WHERE ds.\"ID_Departamento\" = ${idDepart}"

        return sql.executeQueryAsList(query)
    }

    Boolean InsertServiciosDepartamentos(int idServicio, int idDepart){
        String query = " INSERT INTO \n" +
                "  public.\"DepartamentoVsServicos\"\n" +
                "(\n" +
                "  \"ID_servicio\",\n" +
                "  \"ID_Departamento\"\n" +
                ")\n" +
                "VALUES (\n" +
                "  ${idServicio},\n" +
                "  ${idDepart}\n" +
                "); "

        return sql.executeQueryInsertUpdate(query)
    }

    List GetServicios(int idResi){
        String query = "SELECT \n" +
                "  S.\"ID_servicio\" AS value,\n" +
                "  S.\"Descripcion\" AS label\n" +
                "FROM \n" +
                "  public.\"Servicios\"  as S\n" +
                "  WHERE S.\"idResidencial\" = ${idResi}"

        println("Mi Query \n"+query)
        return sql.executeQueryAsList(query)
    }

    Map getidResidencial(int idDepa){

        String query = "  SELECT \n" +
                "  R.\"ID_residencial\" AS idResi\n" +
                "FROM \n" +
                "  public.\"Departamentos\"  AS D\n" +
                "  INNER JOIN PUBLIC.\"Torre\" AS T\n" +
                "  ON D.\"ID_torre\" = T.\"ID_torre\"\n" +
                "  INNER JOIN PUBLIC.\"Residencial\" AS R\n" +
                "  ON T.\"ID_residencial\" = R.\"ID_residencial\"\n" +
                "  WHERE D.\"ID_departamento\" = ${idDepa}"

        return sql.executeQueryAsMap(query)
    }

    Boolean InserImgDepartamento(int iddepar, String img){
        String query = " INSERT INTO \n" +
                "  public.\"DepartamentoVSFoto\"\n" +
                "(\n" +
                "  \"idDepartamento\",\n" +
                "  imagen\n" +
                ")\n" +
                "VALUES (\n" +
                "  ${iddepar},\n" +
                "  '${img}'\n" +
                "); "

        return sql.executeQueryInsertUpdate(query)
    }

}
