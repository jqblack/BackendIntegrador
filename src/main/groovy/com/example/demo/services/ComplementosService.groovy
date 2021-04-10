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
}
