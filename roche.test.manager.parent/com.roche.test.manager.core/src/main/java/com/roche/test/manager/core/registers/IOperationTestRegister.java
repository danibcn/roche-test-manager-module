package com.roche.test.manager.core.registers;

import com.roche.test.manager.core.dtos.OperationTestRegisterRequest;

public interface IOperationTestRegister {

    void registerOperationsResultsTest(final OperationTestRegisterRequest operationTestRegisterRequest);
}
