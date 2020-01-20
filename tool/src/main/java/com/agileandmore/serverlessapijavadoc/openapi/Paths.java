/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agileandmore.serverlessapijavadoc.openapi;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author pascal
 */
public class Paths {

    private Map<String, PathItem> paths = new HashMap<>();

    public Map<String, PathItem> getPaths() {
        return paths;
    }

    public void setPaths(Map<String, PathItem> paths) {
        this.paths = paths;
    }

    // essai : ajouter 2 elements dans la map pour voir comment ca sort
    Paths() {
        paths.put("/pets", new PathItem());
        paths.put("/pets/{petId}", new PathItem());
    }
}
