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
                "  \"VentaDisponible\"\n" +
                "FROM \n" +
                "  public.\"Departamentos\" WHERE \"ID_torre\" = ${idTorre}"

        return sql.executeQueryAsList(query)
    }

    Boolean Insert(int idTorre, String nom, int venta, int alquiler, Boolean disponibleVenta, int cantBath, int cantHabi, Boolean amueblado){
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
                "  \"isAmueblado\" \n" +
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
                        "  ${amueblado}\n" +
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
        String query = "SELECT \n" +
                "I.*,\n" +
                "D.\"Nombre_departamento\",\n" +
                "CONCAT(P.\"Nombre\",' ',P.\"Apellido\") AS nombrePersona \n" +
                "FROM PUBLIC.\"Inquilino\" AS I \n" +
                "INNER JOIN PUBLIC.\"Departamentos\" AS D\n" +
                "ON I.\"ID_deparamento\" = D.\"ID_departamento\"\n" +
                "INNER JOIN public.\"Persona\" AS P\n" +
                "ON I.\"idPersona\" = P.\"IdPersona\"\n" +
                "WHERE I.\"idResidencial\" = ${idResi}"

        return sql.executeQueryAsList(query)
    }

}
