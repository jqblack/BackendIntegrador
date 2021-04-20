package com.example.demo.services

import com.example.demo.database.Sql
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ResidencialService {
    @Autowired
    Sql sql

    List getAllResidencial(){
        String query = "SELECT \n" +
                "  \"ID_residencial\" as id,\n" +
                "  nombre,\n" +
                "  \"ID_provincia\",\n" +
                "  \"ID_municipio\",\n" +
                "  \"ID_sector\",\n" +
                "  areacuadrada,\n" +
                "  \"MinimoVenta\",\n" +
                "  \"MinimoAlquiler\",\n" +
                "  \"ID_status\"\n" +
                "FROM \n" +
                "  public.\"Residencial\" ;";


        return sql.executeQueryAsList(query);
    }

    List GetResidencialByOwner(int idUser){
        String query = "SELECT \n" +
                "  R.\"ID_residencial\" AS id,\n" +
                "  R.nombre,\n" +
                "  R.\"ID_provincia\",\n" +
                "  R.\"ID_municipio\",\n" +
                "  R.\"ID_sector\",\n" +
                "  R.areacuadrada,\n" +
                "  R.\"MinimoVenta\",\n" +
                "  R.\"MinimoAlquiler\",\n" +
                "  R.\"ID_status\",\n" +
                "  R.\"imgPortada\"\n" +
                "FROM \n" +
                "  public.\"Residencial\" As R\n" +
                "  INNER JOIN PUBLIC.\"OwnersVsResidencia\" AS RO\n" +
                "  ON R.\"ID_residencial\" = RO.\"Id_residencial\"\n" +
                "  WHERE RO.\"ID_usuario\" = ${idUser}";


        return sql.executeQueryAsList(query);
    }

    Boolean InsertResidencial(String nombre,int pro,int muni, int sector, int area,String imgbase, int idUser){
        String query = "INSERT INTO PUBLIC.\"Residencial\"(\"nombre\",\n" +
                "\"ID_provincia\",\n" +
                "\"ID_municipio\",\n" +
                "\"ID_sector\",\n" +
                "\"areacuadrada\", \"ID_status\", \"imgPortada\")\n" +
                "VALUES('${nombre}',${pro},${muni},${sector},${area},${1},'${imgbase}') RETURNING * ";

        int idResi = sql.executeQueryAsMap(query).ID_residencial

        query = "  INSERT INTO \n" +
                "  public.\"OwnersVsResidencia\"\n" +
                "(\n" +
                "  \"ID_usuario\",\n" +
                "  \"Id_residencial\"\n" +
                ")\n" +
                "VALUES (\n" +
                "  ${idUser},\n" +
                "  ${idResi}\n" +
                ");"

        return sql.executeQueryInsertUpdate(query);
    }

    Boolean UpdateResidencial(String nombre,int pro,int muni, int sector, int area, int ID_resi){
        String query = "UPDATE \n" +
                "  public.\"Residencial\" \n" +
                "SET \n" +
                "  \"nombre\" = '${nombre}',\n" +
                "  \"ID_provincia\" = ${pro},\n" +
                "  \"ID_municipio\" = ${muni},\n" +
                "  \"ID_sector\" = ${sector},\n" +
                "  \"areacuadrada\" =  ${area}\n" +
                "WHERE \n" +
                "  \"ID_residencial\" = ${ID_resi}";

        return sql.executeQueryInsertUpdate(query);
    }

    Boolean DeleteResidencial( int ID_resi){
        String query = "UPDATE \n" +
                "  public.\"Residencial\" \n" +
                "SET \n" +
                "\"ID_status\" = ${2}\n" +
                "WHERE \n" +
                "  \"ID_residencial\" = ${ID_resi}";

        return sql.executeQueryInsertUpdate(query);
    }


    List getResidencialList() {
        String query = "Select * from public.\"Calificacion\"";
        return sql.executeQueryAsList(query)
    }

    Boolean TestInsert(String descrip){
        String query = "INSERT INTO PUBLIC.test(descripcion)VALUES('${descrip}')";

        return sql.executeQueryInsertUpdate(query);
    }



    Map GetNameId(Integer ID){
        String query = "Select * from public.calificacion where id = ${ID}";
        return sql.executeQueryAsMap(query)
    }

    Boolean TestInsertIMG(String imgbase){
        String query ="INSERT INTO \n" +
                "  public.\"testImg\"\n" +
                "(\n" +
                "  \"baseIMG\"\n" +
                ")\n" +
                "VALUES ('${imgbase}');";

        return sql.executeQueryInsertUpdate(query)
    }

    Map GetImg(int ID){
        String query = "SELECT \n" +
                "  t.id,\n" +
                "  encode(t.\"miImg\", 'base64') as imgbase64\n" +
                "FROM \n" +
                "  public.\"testImg\" as t  where t.id = ${ID};";
        return sql.executeQueryAsMap(query)
    }

    List getDataTest(){
        String query = "SELECT \n" +
                "  \"ID\" as id,\n" +
                "  \"ID_Drop1\" as combo1,\n" +
                "  \"ID_Drop2\" as combo2,\n" +
                "  descripcion as entrada,\n" +
                "  fecha\n" +
                "FROM \n" +
                "  public.\"FromTest\";";
        return sql.executeQueryAsList(query)
    }

    Map TestgetIMG(int IDIMG){
        String query = "Select * from public.\"testImg\" where id = ${IDIMG}";
        return sql.executeQueryAsMap(query)
    }

    Boolean TestDate(Date fecha){
        String query = "INSERT INTO \n" +
                "  public.test\n" +
                "(\n" +
                "  id,\n" +
                "  nombre,\n" +
                "  fecha\n" +
                ")\n" +
                "VALUES (\n" +
                "  5,\n" +
                "  'Randiel',\n" +
                " to_date(to_char('${fecha}'::DATE,'dd/mm/yyyy'),'dd/mm/yyyy')\n" +
                ");";
        return sql.executeQueryInsertUpdate(query)
    }
}
