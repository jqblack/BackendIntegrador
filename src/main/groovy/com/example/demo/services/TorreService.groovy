package com.example.demo.services

import com.example.demo.database.Sql
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class TorreService {

    @Autowired
    Sql sql

    List GetAllTorre(int idresi){
        String query = " SELECT \n" +
                "  T.\"ID_torre\" as id,\n" +
                "  T.\"ID_residencial\" as idresidencial,\n" +
                "  T.cantidadniveles, nombre_torre as nombre\n" +
                "FROM \n" +
                "  public.\"Torre\" as T where T.\"ID_residencial\" = ${idresi}"

        return sql.executeQueryAsList(query)
    }

    Boolean Insert(int resi, String nombre, int canti){
        String query = "INSERT INTO \n" +
                "  public.\"Torre\"\n" +
                "(\n" +
                "  \"ID_residencial\",\n" +
                "  nombre_torre,\n" +
                "  cantidadniveles\n" +
                ")\n" +
                "VALUES (\n" +
                "  ${resi},\n" +
                "  '${nombre}',\n" +
                "  ${canti}\n" +
                ");  "

        return sql.executeQueryInsertUpdate(query)
    }

    Boolean Update(int resi, String nombre, int canti, int idTorre){
        String query = "UPDATE \n" +
                "  public.\"Torre\" \n" +
                "SET \n" +
                "  \"ID_residencial\" = ${resi},\n" +
                "  nombre_torre = '${nombre}',\n" +
                "  cantidadniveles = ${canti}\n" +
                "WHERE \n" +
                "  \"ID_torre\" = ${idTorre}\n" +
                ";"

        return sql.executeQueryInsertUpdate(query)
    }
}
