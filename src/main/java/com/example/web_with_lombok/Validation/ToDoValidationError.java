package com.example.web_with_lombok.Validation;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

public class ToDoValidationError {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String > errors = new ArrayList<>();

    private final String errrMessage;

    public ToDoValidationError(String errrMessage) {
        this.errrMessage = errrMessage;
    }

    public void addValidationError(String error) {
        errors.add(error);
    }

    public List<String> getErrors () {
        return  errors;
    }

    public String getErrorMessage() {
        return errrMessage;
    }
}
