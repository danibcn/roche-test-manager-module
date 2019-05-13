package com.roche.test.manager.core.registers;

import com.roche.test.manager.core.dtos.OperationTestRegisterRequest;

public class OperationTestLogger implements IOperationTestRegister {
    @Override
    public void registerOperationsResultsTest(final OperationTestRegisterRequest operationTestRegisterRequest) {
        System.out.println(operationTestRegisterRequest.toString());
    }
}
