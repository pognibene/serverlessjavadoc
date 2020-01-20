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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a complete Open Api 3.x.x document. This can be serialized either
 * in Json or in Yaml.
 *
 */
public class OpenApi {

    private String openapi = "3.0.0";
    private List<Server> servers = new ArrayList<>();
    private Map<String, PathItem> paths = new HashMap<>();
    private Components components = new Components();

    //FIXME essai
//    public OpenApi() {
//        paths.put("/pets", new PathItem());
//        paths.put("/pets/{petId}", new PathItem());
//    }

    // probably list of tags to group endpoints together, including documentation??
    public String getOpenapi() {
        return openapi;
    }

    public void setOpenapi(String openapi) {
        this.openapi = openapi;
    }

    public List<Server> getServers() {
        return servers;
    }

    public void setServers(List<Server> servers) {
        this.servers = servers;
    }

    public Map<String, PathItem> getPaths() {
        return paths;
    }

    public void setPaths(Map<String, PathItem> paths) {
        this.paths = paths;
    }

    public Components getComponents() {
        return components;
    }

    public void setComponents(Components components) {
        this.components = components;
    }

}
