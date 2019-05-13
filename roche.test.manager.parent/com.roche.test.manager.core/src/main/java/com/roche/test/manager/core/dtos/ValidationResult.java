package com.roche.test.manager.core.dtos;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ValidationResult {
    private boolean valid;
    private String message;
}
