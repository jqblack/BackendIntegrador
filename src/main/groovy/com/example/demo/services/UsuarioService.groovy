package com.example.demo.services

import com.example.demo.database.Sql
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UsuarioService {

    @Autowired
    Sql sql

    Boolean InsertUser(String nombre, String apellido, int sexo, String user, String pass,String numCuenta){

        String query = "  INSERT INTO \n" +
                "  public.\"Persona\"\n" +
                "(\n" +
                "  \"Nombre\",\n" +
                "  \"Apellido\",\n" +
                "  \"ID_Sexo\"\n" +
                ")\n" +
                "VALUES (\n" +
                "  '${nombre}',\n" +
                "  '${apellido}',\n" +
                "  ${sexo}\n" +
                ") RETURNING IdPersona;"
        Map MapPersona = [:];

        MapPersona = sql.executeQueryAsMap(query);
        if(!MapPersona != [:]){
            query = "INSERT INTO \n" +
                    "  public.\"Usuario\"\n" +
                    "(\n" +
                    "  \"userName\",\n" +
                    "  password,\n" +
                    "  \"IdPersona\",\n" +
                    "  \"NumeroCuenta\",\n" +
                    "  \"idStatusUsuario\"\n" +
                    ")\n" +
                    "VALUES (\n" +
                    "  '${user}',\n" +
                    "  '${pass}',\n" +
                    "  ${MapPersona.IdPersona},\n" +
                    "  '${numCuenta}',\n" +
                    "  ${1}\n" +
                    ");"

            return sql.executeQueryInsertUpdate(query);
        }
        else{
            return false;
        }
    }

    Map Login(String user, String pass){
        String query = "SELECT U.* FROM PUBLIC.\"Usuario\" AS U WHERE U.\"userName\" = '${user}' AND U.password = '${pass}'";

        return sql.executeQueryAsMap(query);
    }

    List GetPermissionUser(int idUser){
        String query = "SELECT \n" +
                " R.*,\n" +
                " TU.\"idTipoUsuario\" as descripcionTipoUser,\n" +
                "  RE.nombre as nombre_residencial \n" +
                " FROM PUBLIC.\"Roles\" AS R\n" +
                "INNER JOIN PUBLIC.\"TipoUsuario\" AS TU \n" +
                "ON R.\"idTipo\" = TU.\"idTipoUsuario\"\n" +
                "INNER JOIN PUBLIC.\"Residencial\" AS RE \n" +
                "ON R.\"ID_residencial\" = RE.\"ID_residencial\"\n" +
                "WHERE R.\"idUsuario\" = ${idUser}";

        return sql.executeQueryAsList(query)
    }




}
