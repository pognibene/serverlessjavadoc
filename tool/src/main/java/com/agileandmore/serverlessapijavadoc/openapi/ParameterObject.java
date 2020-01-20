/*
    Copyright 2020 Pascal Ognibene (pognibene@gmail.com)

    This file is part of The serverless javadoc api tool.

    The javadoc api tool is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    The javadoc api tool is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Foobar.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.agileandmore.serverlessapijavadoc.openapi;

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
