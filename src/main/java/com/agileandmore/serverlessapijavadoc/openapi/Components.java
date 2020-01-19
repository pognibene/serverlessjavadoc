/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agileandmore.serverlessapijavadoc.openapi;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author pascal
 */
public class Components {
    
    private Map<String, JsonNode> schemas = new HashMap<>();  

    public Map<String, JsonNode> getSchemas() {
        return schemas;
    }

    public void setSchemas(Map<String, JsonNode> schemas) {
        this.schemas = schemas;
    }
     
}
