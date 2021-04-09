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

    Boolean InsertResidencial(String nombre,int pro,int muni, int sector, int area){
        String query = "INSERT INTO PUBLIC.\"Residencial\"(\"nombre\",\n" +
                "\"ID_provincia\",\n" +
                "\"ID_municipio\",\n" +
                "\"ID_sector\",\n" +
                "\"areacuadrada\", \"ID_status\")\n" +
                "VALUES('${nombre}',${pro}),${muni},${sector},${area},${1}";

        return sql.executeQueryInsertUpdate(query);
    }

    Boolean UpdateResidencial(String nombre,int pro,int muni, int sector, int area, int ID_resi){
        String query = "UPDATE \n" +
                "  public.\"Residencial\" \n" +
                "SET \n" +
                "  \"nombre\" = ${nombre},\n" +
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

    List Get_Provincias(){
        String query = "SELECT \n" +
                "  id as value,\n" +
                "  descripcion as label\n" +
                "FROM \n" +
                "  public.provincias2;"

        return sql.executeQueryAsList(query);
    }
    Boolean TestInsertIMG(byte[] imgbase){
        String query ="INSERT INTO \n" +
                "  public.\"testImg\"\n" +
                "(\n" +
                "  \"baseIMG\"\n" +
                ")\n" +
                "VALUES (${imgbase});";

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
}
