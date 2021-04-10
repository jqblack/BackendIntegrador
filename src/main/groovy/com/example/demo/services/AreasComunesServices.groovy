package com.example.demo.services

import com.example.demo.database.Sql
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AreasComunesServices {

    @Autowired
    Sql sql

    Boolean Insert(String descripcion){
        String query = "INSERT INTO \n" +
                "  public.\"AreasComunes\"\n" +
                "(\n" +
                "  descripcion\n" +
                ")\n" +
                "VALUES (\n" +
                "  '${descripcion}'\n" +
                ");"

        return sql.executeQueryInsertUpdate(query);
    }

    Boolean Update(int IdArea, String descri){
        String query = "UPDATE \n" +
                "  public.\"AreasComunes\" \n" +
                "SET \n" +
                "  descripcion = '${descri}',\n" +
                "WHERE \n" +
                "  \"ID_areaComunes\" = ${IdArea}\n" +
                ";"
        return sql.executeQueryInsertUpdate(query);
    }

    Boolean Delete(int IdArea){
        String query = "UPDATE \n" +
                "  public.\"AreasComunes\" \n" +
                "SET \n" +
                "  activo = false\n" +
                "WHERE \n" +
                "  \"ID_areaComunes\" = ${IdArea}\n" +
                ";"
        return sql.executeQueryInsertUpdate(query);
    }

    List GetAreasComunes(int idResidencial){
        String query = "SELECT \n" +
                "  AR.\"ID_areaComunes\",\n" +
                "  AR.\"ID_residencial\"\n" +
                "FROM \n" +
                "  public.\"AreaComunesvsResidencial\" AS AR  WHERE AR.\"ID_residencial\" = ${idResidencial}"

        return sql.executeQueryAsList(query);
    }
}
