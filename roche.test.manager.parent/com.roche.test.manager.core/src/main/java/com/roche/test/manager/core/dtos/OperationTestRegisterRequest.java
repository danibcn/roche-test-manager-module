package com.roche.test.manager.core.dtos;

import com.roche.test.manager.core.entities.ClientId;
import com.roche.test.manager.core.entities.TestId;
import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
public class OperationTestRegisterRequest {

    private ClientId clientId;
    private TestId testId;
    private OperationResult operationResult;


}
