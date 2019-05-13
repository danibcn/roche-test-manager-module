package com.roche.test.manager.core.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class OperationResult {

    private String message;
    private long value;

}
