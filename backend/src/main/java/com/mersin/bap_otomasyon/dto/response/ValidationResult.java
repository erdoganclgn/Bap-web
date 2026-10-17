package com.mersin.bap_otomasyon.dto.response;

import com.mersin.bap_otomasyon.dto.request.ValidationError;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {
    private boolean valid;
    private List<ValidationError> errors = new ArrayList<>();

    public ValidationResult() {}

    public ValidationResult(boolean valid) {
        this.valid = valid;
    }

    public boolean isValid() { return valid; }
    public void setValid(boolean valid) { this.valid = valid; }
    public List<ValidationError> getErrors() { return errors; }
    public void setErrors(List<ValidationError> errors) { this.errors = errors; }

    public void addError(ValidationError error) {
        this.errors.add(error);
    }
}