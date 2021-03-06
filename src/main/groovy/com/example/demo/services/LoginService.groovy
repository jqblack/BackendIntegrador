package com.example.demo.services

import com.example.demo.database.Sql
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class LoginService {

    @Autowired
    Sql sql

    Map VerifiedUser(String user, String pass){

        Map mapa = [:];

        String query = "SELECT * FROM PUBLIC.\"Usuario\" AS U \n" +
                "WHERE u.\"userName\" = '${user}' AND U.password = '${pass}'";

        mapa = sql.executeQueryAsMap(query);
        if(mapa ==  [:]){
            mapa.put("key","-3");
        }
        else{
            List listPermissions;

            query = "SELECT R.\"idTipo\" as value, R.\"ID_residencial\", \n" +
                    "concat(RE.nombre,' - ',TU.tipo) as label FROM PUBLIC.\"Roles\" AS R\n" +
                    "INNER JOIN PUBLIC.\"TipoUsuario\" AS TU \n" +
                    "ON R.\"idTipo\" = TU.\"idTipoUsuario\"\n" +
                    "INNER JOIN PUBLIC.\"Residencial\" AS RE \n" +
                    "ON R.\"ID_residencial\" = RE.\"ID_residencial\"\n" +
                    "WHERE R.\"idUsuario\" = ${mapa.idUsuario}";

            listPermissions = sql.executeQueryAsList(query);

            println(query)

            mapa.put("Permisos",listPermissions);

            query = "SELECT \n" +
                    "count(R.*) as cantPermiso\n" +
                    "FROM PUBLIC.\"Roles\" AS R\n" +
                    "WHERE R.\"idUsuario\" = ${mapa.idUsuario}"

             mapa.put("cantPermisos",sql.executeQueryAsMap(query).cantPermiso)

        }

        return mapa;
    }
}
