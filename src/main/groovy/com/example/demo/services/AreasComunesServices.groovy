package com.example.demo.services

import com.example.demo.database.Sql
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AreasComunesServices {

    @Autowired
    Sql sql

    Boolean Insert(){
        String query = ""

        return sql.executeQueryInsertUpdate(query);
    }
}
