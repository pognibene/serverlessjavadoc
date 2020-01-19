/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agileandmore.serverlessapijavadoc.openapi;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author pascal
 */
public class ResponseObject {
    private String description = "";
    //private Map<String, HeaderObject> headers = new HashMap<>();
    private Map<String, MediaTypeObject> content = new HashMap<>();
    //links ?

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, MediaTypeObject> getContent() {
        return content;
    }

    public void setContent(Map<String, MediaTypeObject> content) {
        this.content = content;
    }
    
    
}
