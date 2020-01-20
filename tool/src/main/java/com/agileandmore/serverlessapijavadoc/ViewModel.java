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
package com.agileandmore.serverlessapijavadoc;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Represents one class used to exchange messages between the client and the API.
 * Is marshalled/unmarshalled to Json.
 */
public class ViewModel {
    
    //including a full Json example (possibily auto generated)
    private String qualifiedName = "";
    private List<ViewAttribute> attributes = new ArrayList<>();
    private String documentation = "";
    private String jsonExample = "";

    public String getQualifiedName() {
        return qualifiedName;
    }

    public void setQualifiedName(String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }

    public List<ViewAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ViewAttribute> attributes) {
        this.attributes = attributes;
    }

    public String getDocumentation() {
        return documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }
    
    
    
    
}
