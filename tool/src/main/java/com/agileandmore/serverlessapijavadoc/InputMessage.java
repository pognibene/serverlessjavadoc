
package com.agileandmore.serverlessapijavadoc;

/**
 *
 * @author pascal
 */
public class InputMessage {
    private String documentation;
    private String qualifiedClassName;
    private Boolean isArray = false;

    public String getDocumentation() {
        return documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    public String getQualifiedClassName() {
        return qualifiedClassName;
    }

    public void setQualifiedClassName(String qualifiedClassName) {
        this.qualifiedClassName = qualifiedClassName;
    }

    public Boolean getIsArray() {
        return isArray;
    }

    public void setIsArray(Boolean isArray) {
        this.isArray = isArray;
    }
    
    
}
