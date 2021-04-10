package com.example.demo.services

import com.example.demo.database.Sql
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ServiciosService {

    @Autowired
    Sql sql

    List GetAllServices(int idResi){
        String query = "SELECT \n" +
                "  \"ID_servicio\" AS id,\n" +
                "  \"Descripcion\",\n" +
                "  cobro,\n" +
                "  pago\n" +
                "FROM \n" +
                "  public.\"Servicios\" as S WHERE S.\"idResidencial\" = ${idResi}";

        return sql.executeQueryAsList(query)
    }

    Boolean Insert(String descri, int cobro, int pago, int idResi){
        String query = "INSERT INTO \n" +
                "  public.\"Servicios\"\n" +
                "(\n" +
                "  \"Descripcion\",\n" +
                "  cobro,\n" +
                "  pago,\n" +
                "  \"idResidencial\"\n" +
                ")\n" +
                "VALUES (\n" +
                "  '${descri}',\n" +
                "  ${cobro},\n" +
                "  ${pago},\n" +
                "  ${idResi}\n" +
                ");"

        return sql.executeQueryInsertUpdate(query)
    }

    Boolean Update(String descri, int cobro, int pago, int idResi, int idServicio){
        String query = "UPDATE \n" +
                "  public.\"Servicios\" \n" +
                "SET \n" +
                "  \"Descripcion\" = '${descri}',\n" +
                "  cobro = ${cobro},\n" +
                "  pago = ${pago},\n" +
                "  \"idResidencial\" = ${idResi}\n" +
                "WHERE \n" +
                "  \"ID_servicio\" = ${idServicio}\n" +
                ";";

        return sql.executeQueryInsertUpdate(query)

    }
}
