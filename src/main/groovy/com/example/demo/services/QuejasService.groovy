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
        String query = "  SELECT \n" +
                "  I.*,\n" +
                "  concat(p.\"Nombre\",' ',p.\"Apellido\") as nompersona\n" +
                "FROM \n" +
                "  public.\"Inquilino\" AS I\n" +
                "  INNER JOIN PUBLIC.\"Usuario\" AS U\n" +
                "  ON I.\"ID_usuario\" = U.\"idUsuario\"\n" +
                "  INNER JOIN PUBLIC.\"Persona\" AS P\n" +
                "  ON U.\"IdPersona\" = P.\"IdPersona\"\n" +
                "  WHERE I.\"ID_usuario\" = ${idUser}"

        Map mapaUserFrom = sql.executeQueryAsMap(query)

        query = "INSERT INTO \n" +
                "  public.\"historialQuejas\"\n" +
                "(\n" +
                "  \"id_TipoQueja\",\n" +
                "  \"ID_usuarioFrom\",\n" +
                "  \"ID_usuarioTo\",\n" +
                "  \"currentDate\",\n" +
                "  \"ID_EstadoQuejas\",\n" +
                "  \"Descripcion\",  \"idModifiedby\",\n" +
                "  \"modifiedBy\" , \"id_Residencial\", nombrefrom \n" +
                ")\n" +
                "VALUES (\n" +
                "  ${idTipoQueja},\n" +
                "  ${idUsuarioForm},\n" +
                "  ${idUsuarioTo},\n" +
                "  now(),\n" +
                "  ${1},\n" +
                "  '${des}', ${idUser}, '${username}' , ${mapaUserFrom.idResidencial}, '${mapaUserFrom.nompersona}'\n" +
                ");"

        return sql.executeQueryInsertUpdate(query);
    }

    List GetTipoQuejasByInquilino(int idUser){
        String query = "SELECT \n" +
                "  Q.\"Descripcion\" AS label,\n" +
                "  Q.\"ID_TipoQuejas\" AS value\n" +
                "FROM \n" +
                "  public.\"Inquilino\" as I\n" +
                "  INNER JOIN PUBLIC.\"Residencial\" AS R\n" +
                "  ON I.\"idResidencial\" = R.\"ID_residencial\"\n" +
                "  INNER JOIN PUBLIC.\"TipoQuejas\" AS Q\n" +
                "  ON Q.\"idResidencial\" = R.\"ID_residencial\"\n" +
                "  WHERE I.\"ID_usuario\" = ${idUser}\n" +
                "  GROUP BY Q.\"ID_TipoQuejas\""

        println(query)
        return sql.executeQueryAsList(query)
    }

    List cargarpresunto(int idUser){
        String query = "  SELECT \n" +
                "  I.\"idResidencial\"\n" +
                "FROM \n" +
                "  public.\"Inquilino\"  AS I\n" +
                "  WHERE I.\"ID_usuario\" = ${idUser}"

        Map mapa = sql.executeQueryAsMap(query)


        query = "  SELECT \n" +
                "  \"ID_usuario\" as value,\n" +
                "  \"Nombre_departamento\" AS label\n" +
                "FROM \n" +
                "  public.\"Inquilino\"  AS I\n" +
                "  WHERE I.\"idResidencial\" = ${mapa.idResidencial}"

        return sql.executeQueryAsList(query)
    }

    Boolean GetListQuejasbyResidencial(int idUser){
        
        String query = "SELECT \n" +
                "  RO.\"Id_residencial\" AS id\n" +
                "FROM \n" +
                "  public.\"OwnersVsResidencia\" RO\n" +
                "  WHERE RO.\"ID_usuario\" = ${idUser}"

        List listaresi = sql.executeQueryAsList(query)
        List Data = []

        if(listaresi.size() > 0){

            for (int i = 0; i < listaresi.size(); i++) {

                query = "SELECT \n" +
                        "  I.\"Nombre_departamento\" AS departamentofrom,\n" +
                        "  R.nombre AS nombreResi,\n" +
                        "  concat(p.\"Nombre\",' ',p.\"Apellido\") as nomperfrom,\n" +
                        "  H.\"Descripcion\" as descri,\n" +
                        "  H.nombrefrom AS nomperto\n" +
                        "FROM \n" +
                        "  public.\"historialQuejas\" AS H\n" +
                        "  INNER JOIN PUBLIC.\"Usuario\" AS U\n" +
                        "  ON H.\"ID_usuarioFrom\" = U.\"idUsuario\"\n" +
                        "  INNER JOIN PUBLIC.\"Persona\" AS P\n" +
                        "  ON U.\"IdPersona\" = P.\"IdPersona\"\n" +
                        "  INNER JOIN PUBLIC.\"Inquilino\" AS I\n" +
                        "  ON H.\"ID_usuarioTo\" = I.\"ID_usuario\"\n" +
                        "  INNER JOIN PUBLIC.\"Residencial\" AS R\n" +
                        "  ON H.\"id_Residencial\" = R.\"ID_residencial\"\n" +
                        "  WHERE H.\"id_Residencial\" = ${listaresi[i].id}"

                Data += sql.executeQueryAsList(query)
            }
        }

        return Data

    }


}
