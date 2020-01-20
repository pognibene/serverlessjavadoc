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

import com.fasterxml.jackson.annotation.JsonProperty;

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
