package com.agileandmore.examples.model.views;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("A normalized error message to return when something went wrong in an API call.")
public class ErrorMessage {

    @JsonPropertyDescription("The message of the error.")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
