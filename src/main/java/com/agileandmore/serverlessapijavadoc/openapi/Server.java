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
public class Server {

    private String url = "";
    private String description = "";
    private Map<String, ServerVariable> variables = new HashMap<>();

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, ServerVariable> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, ServerVariable> variables) {
        this.variables = variables;
    }

}
