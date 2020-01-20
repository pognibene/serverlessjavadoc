/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 * @author pascal
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
