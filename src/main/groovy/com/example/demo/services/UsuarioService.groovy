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
        if(!MapPersona.isEmpty()){
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
                    "  ${numCuenta},\n" +
                    "  ${1}\n" +
                    ");"

            return sql.executeQueryInsertUpdate(query);
        }
        else{
            return false;
        }
    }
}
