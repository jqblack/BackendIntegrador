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


    Boolean Insert( String descri, int cantdias){
        String query = "INSERT INTO \n" +
                "  public.\"MantenimientoArea\"\n" +
                "(\n" +
                "  \"Descripcion\",\n" +
                "  \"cantidadDias\"\n" +
                ")\n" +
                "VALUES (\n" +
                "  '${descri}',\n" +
                "  ${cantdias}\n" +
                ");"

        return sql.executeQueryInsertUpdate(query);
    }

    Boolean Update(int idMante, int CantDias){
        String sql = "UPDATE \n" +
                "  public.\"MantenimientoArea\" \n" +
                "SET \n" +
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
                "  M.\"Descripcion\"\n" +
                "FROM \n" +
                "  public.\"MantenimientovsResidencial\" AS MR\n" +
                "  INNER JOIN PUBLIC.\"Residencial\" AS R \n" +
                "  ON R.\"ID_residencial\" = ${idResi}\n" +
                "  INNER JOIN PUBLIC.\"MantenimientoArea\" AS M\n" +
                "  ON M.\"ID_TipoMantenimiento\" = MR.\"idMatenimiento\" "

        return sql.executeQueryAsList(query);
    }
}
