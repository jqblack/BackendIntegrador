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

            query = "SELECT R.*, TU.tipo, RE.nombre FROM PUBLIC.\"Roles\" AS R\n" +
                    "INNER JOIN PUBLIC.\"TipoUsuario\" AS TU \n" +
                    "ON R.\"idTipo\" = TU.\"idTipoUsuario\"\n" +
                    "INNER JOIN PUBLIC.\"Residencial\" AS RE \n" +
                    "ON R.\"ID_residencial\" = RE.\"ID_residencial\"\n" +
                    "WHERE R.\"idUsuario\" =  ${mapa.idUsuario}";

            listPermissions = sql.executeQueryAsList(query);

            mapa.put("Permisos",listPermissions);
        }

        return mapa;
    }
}
