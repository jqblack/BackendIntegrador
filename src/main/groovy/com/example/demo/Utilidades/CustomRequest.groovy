package com.example.demo.Utilidades

import groovy.json.JsonSlurper

class CustomRequest{
    Map f_data
    String f_key


    Integer ValidateKey(String MyKey){
print(MyKey)
        if(MyKey.equals("ApiRandiel2021")){
            return 1;
        }
        else{
            return -1;
        }
    }

    /*CustomRequest(String base64) {
        String rs = new String(base64.decodeBase64())
        Map response
        JsonSlurper parser = new JsonSlurper()
        response = parser.parseText(rs);

        println(response.key)
        //println(test);

    }*/

}
