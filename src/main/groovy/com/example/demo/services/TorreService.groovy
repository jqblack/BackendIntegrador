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
                "  T.descripcion\n" +
                "FROM \n" +
                "  public.\"Torre\" as T where T.\"ID_residencial\" = ${idresi}"

        return sql.executeQueryAsList(query)
    }
}
