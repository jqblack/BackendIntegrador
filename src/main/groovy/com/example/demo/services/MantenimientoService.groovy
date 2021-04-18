package com.example.demo.services

import com.example.demo.database.Sql
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MantenimientoService {

    @Autowired
    Sql sql


    Boolean Insert( String descri, int cantdias, List ListResi){
        String query = "INSERT INTO \n" +
                "  public.\"MantenimientoArea\"\n" +
                "(\n" +
                "  \"Descripcion\",\n" +
                "  \"cantidadDias\"\n" +
                ")\n" +
                "VALUES (\n" +
                "  '${descri}',\n" +
                "  ${cantdias}\n" +
                ") RETURNING \"ID_TipoMantenimiento\";"

        Map mapa = [:]
        mapa = sql.executeQueryAsMap(query);

        if(mapa != [:]){

            for (int i = 0; i < ListResi.size(); i++) {
                query = "INSERT INTO \n" +
                        "  public.\"MantenimientovsResidencial\"\n" +
                        "(\n" +
                        "  \"idMatenimiento\",\n" +
                        "  \"idResidencial\"\n" +
                        ")\n" +
                        "VALUES (\n" +
                        "  ${mapa.ID_TipoMantenimiento},\n" +
                        "  ${ListResi[i]}\n" +
                        ");"
                sql.executeQueryInsertUpdate(query)
            }

            return true
        }
        else{
            return false
        }
    }

    Boolean Update(int idMante, int CantDias, String des){
        String sql = "UPDATE \n" +
                "  public.\"MantenimientoArea\" \n" +
                "SET \n" +
                "  \"Descripcion\" = '${des}',\n" +
                "  \"cantidadDias\" = ${CantDias}\n" +
                "WHERE \n" +
                "  \"ID_TipoMantenimiento\" = ${idMante}\n" +
                ";"
        return sql.executeQueryInsertUpdate(query);
    }

    List GetMantenimientos(int idResi){
        String query = "SELECT \n" +
                "  MR.\"idMatenimiento\",\n" +
                "  MR.\"idResidencial\",\n" +
                "  M.*\n" +
                "FROM \n" +
                "  public.\"MantenimientovsResidencial\" AS MR\n" +
                "  INNER JOIN PUBLIC.\"Residencial\" AS R \n" +
                "  ON R.\"ID_residencial\" = MR.\"idResidencial\" \n" +
                "  INNER JOIN PUBLIC.\"MantenimientoArea\" AS M\n" +
                "  ON M.\"ID_TipoMantenimiento\" = MR.\"idMatenimiento\" \n" +
                "  WHERE R.\"ID_residencial\" = ${idResi}"

        print(query)

        return sql.executeQueryAsList(query);
    }
}
