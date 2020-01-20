/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agileandmore.serverlessapijavadoc.openapi;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author pascal
 */
public class ServerVariable {

    @JsonProperty("enum")
    String s_enum = "";
    
    @JsonProperty("default")
    private String s_default = "";
    
    private String description = "";

    public String getS_enum() {
        return s_enum;
    }

    public void setS_enum(String s_enum) {
        this.s_enum = s_enum;
    }

    public String getS_default() {
        return s_default;
    }

    public void setS_default(String s_default) {
        this.s_default = s_default;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
