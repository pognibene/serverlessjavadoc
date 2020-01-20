package com.agileandmore.serverlessapijavadoc;

/**
 *
 * @author pascal
 */
public class PathOrQueryParam {
    private String name;
    private String documentation;
    //FIXME for now I don't fill this, but I should
    // need another parameter in the annotation/tag...
    private Boolean required = true;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocumentation() {
        return documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }
    
    
    
    
}
