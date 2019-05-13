package com.roche.test.manager.core.dtos;

import com.roche.test.manager.core.entities.ClientId;
import com.roche.test.manager.core.entities.TestId;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class OperationsResult {

    private TestId testId;
    private ClientId clientId;
    private List<OperationResult> operationResult;


    public OperationsResult(final TestId testId, final ClientId clientId, final List<OperationResult> operationResultData) {
        this.testId = testId;
        this.clientId = clientId;
        this.operationResult = operationResultData;
    }

    public List<OperationResult> getOperationResult() {
        return Collections.unmodifiableList(new ArrayList<>(operationResult));
    }

}
