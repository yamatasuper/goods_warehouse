package com.goods.product.task2.search;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.validation.constraints.NotBlank;

public class SearchCriteriaDTO {

    @NotBlank(message = "Field name cannot be blank")
    private String field;

    @JsonSerialize(using = CustomValueSerializer.class)
    @JsonDeserialize(using = CustomValueDeserializer.class)
    private Object value;

    @NotBlank(message = "Operation must be provided")
    private String operation;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
