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

    Boolean Insert(String descripcion, int idResi, String nombre){
        String query = "INSERT INTO \n" +
                "  public.\"AreasComunes\"\n" +
                "(\n" +
                "  descripcion,\n" +
                "  nombre\n" +
                ")\n" +
                "VALUES (\n" +
                " '${descripcion}',\n" +
                "  '${nombre}'\n" +
                ") RETURNING \"ID_areaComunes\";"

        Map mapa = [:]
        mapa = sql.executeQueryAsMap(query)

        if(mapa != [:]){
            query = "\n" +
                    "INSERT INTO \n" +
                    "  public.\"AreaComunesvsResidencial\"\n" +
                    "(\n" +
                    "  \"ID_areaComunes\",\n" +
                    "  \"ID_residencial\"\n" +
                    ")\n" +
                    "VALUES (\n" +
                    "  ${mapa.ID_areaComunes},\n" +
                    "  ${idResi}\n" +
                    ");"

            return sql.executeQueryInsertUpdate(query);
        }
        else{
            return false
        }


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
