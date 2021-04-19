package com.example.demo.services

import com.example.demo.database.Sql
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SolicitudService {

    @Autowired
    Sql sql

    List getfirstlist(){
        String query = "SELECT \n" +
                "R.nombre,\n" +
                "R.\"ID_residencial\" AS idResi,\n" +
                "P.descripcion AS nomProvincia,\n" +
                "D.*\n" +
                "FROM PUBLIC.\"Departamentos\" AS D\n" +
                "INNER JOIN PUBLIC.\"Torre\" AS T \n" +
                "ON D.\"ID_torre\" = T.\"ID_torre\"\n" +
                "INNER JOIN PUBLIC.\"Residencial\" AS R\n" +
                "ON T.\"ID_residencial\" = R.\"ID_residencial\"\n" +
                "INNER JOIN PUBLIC.\"Provincia\" AS P\n" +
                "ON R.\"ID_provincia\" = P.\"ID_provincia\" WHERE D.\"Disponible\" = TRUE\n" +
                "LIMIT 12"

        return sql.executeQueryAsList(query)
    }

    List getFilterList(int idPro, int precio){
        String query = "SELECT \n" +
                "R.nombre as nombreResi,\n" +
                "R.\"ID_residencial\" AS idResi,\n" +
                "P.descripcion AS nomProvincia,\n" +
                "D.*\n" +
                "FROM PUBLIC.\"Departamentos\" AS D\n" +
                "INNER JOIN PUBLIC.\"Torre\" AS T \n" +
                "ON D.\"ID_torre\" = T.\"ID_torre\"\n" +
                "INNER JOIN PUBLIC.\"Residencial\" AS R\n" +
                "ON T.\"ID_residencial\" = R.\"ID_residencial\"\n" +
                "INNER JOIN PUBLIC.\"Provincia\" AS P\n" +
                "ON R.\"ID_provincia\" = P.\"ID_provincia\"\n" +
                "WHERE D.\"Disponible\" = TRUE\n" +
                "AND R.\"ID_provincia\" = ${idPro} AND D.\"PrecioAlquiler\" <= ${precio}"

        retun sql.executeQueryAsList(query)
    }

}
