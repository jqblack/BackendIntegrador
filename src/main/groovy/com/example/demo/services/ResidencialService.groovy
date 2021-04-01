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

    List getResidencialList() {
        String query = "Select * from public.test";
        return sql.executeQueryAsList(query)
    }

    Map GetNameId(Integer ID){
        String query = "Select * from public.test where id = ${ID}";
        return sql.executeQueryAsMap(query)
    }
}
