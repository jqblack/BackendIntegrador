package com.example.demo.services

import com.example.demo.database.Sql
import com.sun.org.apache.xpath.internal.operations.Bool
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class QuejasService {

    @Autowired
    Sql sql

    List GetTiposQuejas(int idResi){
        String query = "SELECT \n" +
                "  \"ID_TipoQuejas\",\n" +
                "  \"Descripcion\" as descripcion,\n" +
                "  \"CantAdvertencia\",\n" +
                "  \"LimitePenalizacion\",\n" +
                "  \"CostoPenalizacion\",\n" +
                "  \"ID_dirigido\",\n" +
                "  \"idResidencial\"\n" +
                "FROM \n" +
                "  public.\"TipoQuejas\" as TQ WHERE TQ.\"idResidencial\" = ${idResi}"
        return sql.executeQueryAsList(query);
    }

    Boolean Insert(String des, int cantAdver, int limite, int costo, int diriguido, int idresi){
        String query = "INSERT INTO \n" +
                "  public.\"TipoQuejas\"\n" +
                "(\n" +
                "  \"Descripcion\",\n" +
                "  \"CantAdvertencia\",\n" +
                "  \"LimitePenalizacion\",\n" +
                "  \"CostoPenalizacion\",\n" +
                "  \"ID_dirigido\",\n" +
                "  \"idResidencial\"\n" +
                ")\n" +
                "VALUES (\n" +
                "  '${des}',\n" +
                "  ${cantAdver},\n" +
                "  ${limite},\n" +
                "  ${costo},\n" +
                "  ${diriguido},\n" +
                "  ${idresi}\n" +
                ");"

        return sql.executeQueryInsertUpdate(query)
    }

    Boolean Update(String des, int cantAdver, int limite, int costo, int diriguido, int idresi, int idTipoQueja){
        String query = "UPDATE \n" +
                "  public.\"TipoQuejas\" \n" +
                "SET \n" +
                "  \"Descripcion\" = '${des}',\n" +
                "  \"CantAdvertencia\" = ${cantAdver},\n" +
                "  \"LimitePenalizacion\" = ${limite},\n" +
                "  \"CostoPenalizacion\" = ${costo},\n" +
                "  \"ID_dirigido\" = ${diriguido},\n" +
                "  \"idResidencial\" = ${idresi}\n" +
                "WHERE \n" +
                "  \"ID_TipoQuejas\" = ${idTipoQueja}\n" +
                ";"

        return sql.executeQueryInsertUpdate(query)
    }

    Boolean InsertQueja(int idTipoQueja, int idUsuarioForm, int idUsuarioTo, String des, int idUser, String username){
        String query = "INSERT INTO \n" +
                "  public.\"historialQuejas\"\n" +
                "(\n" +
                "  \"id_TipoQueja\",\n" +
                "  \"ID_usuarioFrom\",\n" +
                "  \"ID_usuarioTo\",\n" +
                "  \"currentDate\",\n" +
                "  \"ID_EstadoQuejas\",\n" +
                "  \"Descripcion\",  \"idModifiedby\",\n" +
                "  \"modifiedBy\"\n" +
                ")\n" +
                "VALUES (\n" +
                "  ${idTipoQueja},\n" +
                "  ${idUsuarioForm},\n" +
                "  ${idUsuarioTo},\n" +
                "  now(),\n" +
                "  ${1},\n" +
                "  '${des}', ${idUser}, '${username}'\n" +
                ");"

        return sql.executeQueryInsertUpdate(query);
    }
}
