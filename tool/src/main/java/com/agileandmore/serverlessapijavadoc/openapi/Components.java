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

import com.fasterxml.jackson.databind.JsonNode;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author pascal
 */
public class Components {
    
    private Map<String, JsonNode> schemas = new HashMap<>();  

    public Map<String, JsonNode> getSchemas() {
        return schemas;
    }

    public void setSchemas(Map<String, JsonNode> schemas) {
        this.schemas = schemas;
    }
     
}
