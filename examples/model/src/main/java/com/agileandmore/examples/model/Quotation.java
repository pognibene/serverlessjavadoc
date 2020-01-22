package com.agileandmore.examples.model;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonClassDescription("A Quotation when a user wants to buy something.")
public class Quotation {

    public Quotation() {
    }

    @JsonPropertyDescription("The ID of the user this quotation is attached to.")
    private String userId;

    @JsonPropertyDescription("The quotation reference number.")
    private String referenceNumber;

    @JsonPropertyDescription("Amount for the quotation.")
    private float amount;

    /*
    Of course, you can have nested complex attributes.
    Provided they have annotations, they will be documented as well in the
    generated json schemas.
     */
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

}
