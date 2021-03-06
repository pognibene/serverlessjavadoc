/*
    Copyright 2020 Pascal Ognibene (pognibene@gmail.com)

    This file is part of The serverless api javadoc api tool (Aka SAJ).

    SAJ is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    SAJ is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with SAJ.  If not, see <https://www.gnu.org/licenses/>.
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
    private List<PathOrQueryParam> headerInParams = new ArrayList<>();
    //private List<PathOrQueryParam> headerOutParams = new ArrayList<>();
    private String documentation = "";
    private String name;
    private String uriPath;
    private String method;
    private Boolean cors;

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

    public List<PathOrQueryParam> getHeaderInParams() {
        return headerInParams;
    }

    public void setHeaderInParams(List<PathOrQueryParam> headerInParams) {
        this.headerInParams = headerInParams;
    }

//    public List<PathOrQueryParam> getHeaderOutParams() {
//        return headerOutParams;
//    }
//
//    public void setHeaderOutParams(List<PathOrQueryParam> headerOutParams) {
//        this.headerOutParams = headerOutParams;
//    }

}
