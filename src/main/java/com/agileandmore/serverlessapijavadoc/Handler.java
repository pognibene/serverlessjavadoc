/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agileandmore.serverlessapijavadoc;

import java.util.ArrayList;
import java.util.List;

/**
 * This represents a Handler in the internal model
 */
public class Handler {

    private String qualifiedName = "";
    private List<InputMessage> inputMessages = new ArrayList<>();
    private List<OutputMessage> outputMessages = new ArrayList<>();
    private List<PathOrQueryParam> queryParams = new ArrayList<>();
    private List<PathOrQueryParam> pathParams = new ArrayList<>();
    private String documentation = "";
    private String name;
    private String uriPath;
    private String method;
    private Boolean cors;

    // for responses, I can have multiple responses with different http codes and different output classes
    // and multiple descriptions as well... so my javadoc tags must be enriched like a lot
    //note : I could possibly also have multiple input messages??
    public String getQualifiedName() {
        return qualifiedName;
    }

    public void setQualifiedName(String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }

    public String getDocumentation() {
        return documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUriPath() {
        return uriPath;
    }

    public void setUriPath(String uriPath) {
        this.uriPath = uriPath;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Boolean getCors() {
        return cors;
    }

    public void setCors(Boolean cors) {
        this.cors = cors;
    }

    public List<OutputMessage> getOutputMessages() {
        return outputMessages;
    }

    public void setOutputMessages(List<OutputMessage> outputMessages) {
        this.outputMessages = outputMessages;
    }

    public List<InputMessage> getInputMessages() {
        return inputMessages;
    }

    public void setInputMessages(List<InputMessage> inputMessages) {
        this.inputMessages = inputMessages;
    }

    public List<PathOrQueryParam> getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(List<PathOrQueryParam> queryParams) {
        this.queryParams = queryParams;
    }

    public List<PathOrQueryParam> getPathParams() {
        return pathParams;
    }

    public void setPathParams(List<PathOrQueryParam> pathParams) {
        this.pathParams = pathParams;
    }

}
