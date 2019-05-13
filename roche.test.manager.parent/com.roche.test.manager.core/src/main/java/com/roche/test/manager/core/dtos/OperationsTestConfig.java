package com.roche.test.manager.core.dtos;

import com.roche.test.manager.core.entities.OperationId;
import com.roche.test.manager.core.entities.TestId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OperationsTestConfig {

    private Map<TestId, List<OperationId>> operationsTestConfig;

    public OperationsTestConfig(final Map<TestId, List<OperationId>> operationsTestConfig) {
        this.operationsTestConfig = operationsTestConfig;
    }

    public List<OperationId> getOperations(final TestId testId) {
        return operationsTestConfig != null && operationsTestConfig.get(testId) == null ? new ArrayList<OperationId>() : operationsTestConfig.get(testId);
    }
}
