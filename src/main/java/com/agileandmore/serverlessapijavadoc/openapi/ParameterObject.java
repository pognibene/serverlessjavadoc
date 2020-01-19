/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agileandmore.serverlessapijavadoc.openapi;

/**
 *
 * @author pascal
 */
public class ParameterObject {

    private String name = "";
    private String in = "";
    private String description = "";
    private Boolean required = true;
    private StringSchema schema = null;

    //https://github.com/victools/jsonschema-generator
    // to generate a compatible json schema from java code
    // the simple string schema is used for query parameters and
    // path parameters, at least for now, because we don't have the required
    // annotations to use complex types
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIn() {
        return in;
    }

    public void setIn(String in) {
        this.in = in;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public StringSchema getSchema() {
        return schema;
    }

    public void setSchema(StringSchema schema) {
        this.schema = schema;
    }

}
