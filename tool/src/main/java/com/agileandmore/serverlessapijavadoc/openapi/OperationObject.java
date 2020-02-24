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
package com.agileandmore.serverlessapijavadoc.openapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OperationObject {

    private String summary;
    private String description;
    private String operationId;
    // we don't handle external docs for now
    private List<String> tags = new ArrayList<>();

    // note : we don't handle ReferenceObject. We could if using
    // an interface or abstract top class in this list, but what's the 
    // point exactly? path parameters or query parameters are not
    // supposed to be structured types, otherwise it would be a complete mess.
    // maybe some people are using complex types for query parameter though??
    private List<ParameterObject> parameters = new ArrayList<>();

    private Map<String, ResponseObject> responses = new HashMap<>();

    //private SecurityObject security = null;
    private Map<String, List<String>> security = new HashMap<>();
    //actually my security should be a map<String, array of string>
    // the key is the name of a security scheme.
    // which means that I must define a security scheme somewhere, at least
    // on one of the endpoints to reuse it. The other endpoints can just reuse it by
    // name.
    // @SecurityDef name scheme ...
    // @SecurityRef blabla

    // by default JSR380 annotation will not trigger a validator
    // unless @Valid is added. Which is good, I wan to use them
    // only to generate better schemas, not to fail the parsing
    // should see though is the library is using them to generate
    // better schemas


    private RequestBody requestBody = null;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<ParameterObject> getParameters() {
        return parameters;
    }

    public void setParameters(List<ParameterObject> parameters) {
        this.parameters = parameters;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
    }

    public Map<String, ResponseObject> getResponses() {
        return responses;
    }

    public void setResponses(Map<String, ResponseObject> responses) {
        this.responses = responses;
    }

    public Map<String, List<String>> getSecurity() {
        return security;
    }

    public void setSecurity(Map<String, List<String>> security) {
        this.security = security;
    }
}
