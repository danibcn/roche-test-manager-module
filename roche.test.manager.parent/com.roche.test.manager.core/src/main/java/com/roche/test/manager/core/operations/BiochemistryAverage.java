package com.roche.test.manager.core.operations;

import com.roche.test.manager.core.dtos.OperationResult;
import com.roche.test.manager.core.dtos.TestProperty;

import java.util.List;

public class BiochemistryAverage extends AbstractOperation implements IOperationTestExecutor {


    private static final String MESSAGE = "The average is within correct values";
    private static final int VALUE = 22;

    public BiochemistryAverage(final List<TestProperty> testProperties) {
        this.testProperties = testProperties;
    }

    @Override
    public OperationResult execute() {
        return OperationResult.builder().value(VALUE).message(MESSAGE).build();
    }
}
