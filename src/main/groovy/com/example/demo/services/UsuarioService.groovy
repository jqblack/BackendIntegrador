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

    Boolean InsertUser(String nombre, String apellido, int sexo, String user, String pass,String numCuenta, Boolean isAdmin, String phone){

        String query = "  INSERT INTO \n" +
                "  public.\"Persona\"\n" +
                "(\n" +
                "  \"Nombre\",\n" +
                "  \"Apellido\",\n" +
                "  \"ID_Sexo\" , celular\n" +
                ")\n" +
                "VALUES (\n" +
                "  '${nombre}',\n" +
                "  '${apellido}',\n" +
                "  ${sexo} , '${phone}'\n" +
                ") RETURNING \"IdPersona\";"
        Map MapPersona = [:];



        MapPersona = sql.executeQueryAsMap(query);

        println(MapPersona.get("IdPersona"))

        if(MapPersona != [:]){

            if(isAdmin) {
                query = "INSERT INTO \n" +
                        "  public.\"Usuario\"\n" +
                        "(\n" +
                        "  \"userName\",\n" +
                        "  password,\n" +
                        "  \"IdPersona\",\n" +
                        "  \"NumeroCuenta\",\n" +
                        "  \"idStatusUsuario\",\n" +
                        "  \"isAdmin\"\n" +
                        ")\n" +
                        "VALUES (\n" +
                        "  '${user}',\n" +
                        "  '${pass}',\n" +
                        "  ${MapPersona.IdPersona},\n" +
                        "  '${numCuenta}',\n" +
                        "  ${1}, ${true}\n" +
                        ");"
            }
            else{
                query = "INSERT INTO \n" +
                        "  public.\"Usuario\"\n" +
                        "(\n" +
                        "  \"userName\",\n" +
                        "  password,\n" +
                        "  \"IdPersona\",\n" +
                        "  \"NumeroCuenta\",\n" +
                        "  \"idStatusUsuario\",\n" +
                        "   \"IsClient\" \n" +
                        ")\n" +
                        "VALUES (\n" +
                        "  '${user}',\n" +
                        "  '${pass}',\n" +
                        "  ${MapPersona.IdPersona},\n" +
                        "  '${numCuenta}',\n" +
                        "  ${1}, ${true}\n" +
                        ");"
            }

            println(query)

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

    Boolean simulacionpago(int idUser){
        String query = "UPDATE \n" +
                "  public.\"CuentaPorCobrar\" \n" +
                "SET \n" +
                "  pagado = TRUE\n" +
                "WHERE \n" +
                "  \"Idusuario\" = ${idUser}\n" +
                ";"

        return sql.executeQueryInsertUpdate(query)
    }

    List GetCuentaCobrar(int idUser){
        List ListaCuenta = [];

        String query = "SELECT \n" +
                "CC.*\n" +
                "FROM \n" +
                "PUBLIC.\"CuentaPorCobrar\" AS CC\n" +
                "WHERE CC.pagado = FALSE AND \n" +
                "CC.\"Idusuario\" = ${idUser}"

        List lista = sql.executeQueryAsList(query)

        for (int i = 0; i < lista.size(); i++) {
            if (lista[i].IdTipoCuentaxCobrar == 1){

                query = "SELECT \n" +
                        "CC.*,\n" +
                        "S.\"Descripcion\" AS definicion\n" +
                        "FROM \n" +
                        "PUBLIC.\"CuentaPorCobrar\" AS CC\n" +
                        "INNER JOIN PUBLIC.\"Servicios\" AS S\n" +
                        "ON CC.\"IdReferencia\" = S.\"ID_servicio\" "+
                        "WHERE CC.pagado = FALSE AND \n" +
                        "CC.\"Idusuario\" = ${idUser}\n" +
                        "AND CC.\"IdReferencia\" = ${lista[i].IdReferencia}\n" +
                        "AND CC.fecha = '${lista[i].fecha}'"

                ListaCuenta  += sql.executeQueryAsMap(query)
            }
            else{
                query = "SELECT \n" +
                        "CC.*,\n" +
                        "Q.\"Descripcion\" AS definicion\n" +
                        "FROM \n" +
                        "PUBLIC.\"CuentaPorCobrar\" AS CC\n" +
                        "INNER JOIN PUBLIC.\"TipoQuejas\" AS Q\n" +
                        "ON CC.\"IdReferencia\" = Q.\"ID_TipoQuejas\" "+
                "WHERE CC.pagado = FALSE AND \n" +
                        "CC.\"Idusuario\" = ${idUser}\n" +
                        "AND CC.\"IdReferencia\" = ${lista[i].IdReferencia}\n" +
                        "AND CC.fecha = '${lista[i].fecha}'"
                println(query)
                ListaCuenta += sql.executeQueryAsMap(query)
            }

        }

        return ListaCuenta
    }


    Map getResidencial(int idUser){
        String query = "SELECT \n" +
                "  R.\"ID_residencial\"\n" +
                "FROM \n" +
                "  public.\"Roles\" AS R\n" +
                "  WHERE R.\"idTipo\" = 2 AND R.\"idUsuario\" = ${idUser}"

        return sql.executeQueryAsMap(query)

    }


}
