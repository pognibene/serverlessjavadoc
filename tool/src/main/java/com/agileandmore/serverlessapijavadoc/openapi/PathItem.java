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

import java.util.HashMap;
import java.util.Map;

public class PathItem {

    private String summary;

    private String description;

    private OperationObject get = null;
    private OperationObject put = null;
    private OperationObject post = null;
    private OperationObject delete = null;
    private OperationObject options = null;
    private OperationObject head = null;
    private OperationObject patch = null;
    private OperationObject trace = null;

    //FIXME essai : ajouter 2 operations dans le path
//    public PathItem() {
//        get = new OperationObject();
//        post = new OperationObject();
//    }
    // we don't use parameters here as there no good reason
    // to have common parameters for all operations
    // we don't use servers here, can't see the use of it
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

    public OperationObject getGet() {
        return get;
    }

    public void setGet(OperationObject get) {
        this.get = get;
    }

    public OperationObject getPut() {
        return put;
    }

    public void setPut(OperationObject put) {
        this.put = put;
    }

    public OperationObject getPost() {
        return post;
    }

    public void setPost(OperationObject post) {
        this.post = post;
    }

    public OperationObject getDelete() {
        return delete;
    }

    public void setDelete(OperationObject delete) {
        this.delete = delete;
    }

    public OperationObject getOptions() {
        return options;
    }

    public void setOptions(OperationObject options) {
        this.options = options;
    }

    public OperationObject getHead() {
        return head;
    }

    public void setHead(OperationObject head) {
        this.head = head;
    }

    public OperationObject getPatch() {
        return patch;
    }

    public void setPatch(OperationObject patch) {
        this.patch = patch;
    }

    public OperationObject getTrace() {
        return trace;
    }

    public void setTrace(OperationObject trace) {
        this.trace = trace;
    }

}
