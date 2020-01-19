/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agileandmore.serverlessapijavadoc;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pascal
 * Represents one class used to exchange messages between the client and the API.
 * Is marshalled/unmarshalled to Json.
 */
public class ViewModel {
    
    //including a full Json example (possibily auto generated)
    private String qualifiedName = "";
    private List<ViewAttribute> attributes = new ArrayList<>();
    private String documentation = "";
    private String jsonExample = "";

    public String getQualifiedName() {
        return qualifiedName;
    }

    public void setQualifiedName(String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }

    public List<ViewAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ViewAttribute> attributes) {
        this.attributes = attributes;
    }

    public String getDocumentation() {
        return documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }
    
    
    
    
}
