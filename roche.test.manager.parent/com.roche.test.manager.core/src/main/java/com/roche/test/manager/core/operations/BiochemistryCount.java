package com.roche.test.manager.core.operations;

import com.roche.test.manager.core.dtos.OperationResult;
import com.roche.test.manager.core.dtos.TestProperty;

import java.util.List;

public class BiochemistryCount extends AbstractOperation implements IOperationTestExecutor {


    private static final String MESSAGE = "The count is within correct values";
    private static final int VALUE = 434;

    public BiochemistryCount(final List<TestProperty> testProperties) {
        this.testProperties = testProperties;
    }

    @Override
    public OperationResult execute() {
        return OperationResult.builder().value(VALUE).message(MESSAGE).build();
    }
}
