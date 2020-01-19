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
public class MediaTypeObject {
    //TODO examples and other attributes
    //examples
    private RefSchema schema = new RefSchema();

    public RefSchema getSchema() {
        return schema;
    }

    public void setSchema(RefSchema schema) {
        this.schema = schema;
    }

}
