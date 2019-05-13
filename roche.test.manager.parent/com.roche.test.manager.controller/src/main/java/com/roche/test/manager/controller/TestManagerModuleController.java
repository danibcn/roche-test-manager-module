package com.roche.test.manager.controller;

import com.roche.test.manager.controller.exceptions.TestDataIncompleteException;
import com.roche.test.manager.core.dtos.*;
import com.roche.test.manager.core.entities.ClientId;
import com.roche.test.manager.core.entities.TestId;
import com.roche.test.manager.core.registers.IOperationTestRegister;
import com.roche.test.manager.core.usecases.IOperationsExecutorUseCase;
import com.roche.test.manager.core.usecases.OperationsExecutorUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/testmanager")
public class TestManagerModuleController {

    private static final String NOT_VALIDS_TEST_VALUES = "Not valids test values";
    private IOperationsExecutorUseCase operationsExecutorUseCase;
    private IOperationTestRegister operationTestRegister;

    public TestManagerModuleController(final IOperationsExecutorUseCase operationsExecutorUseCase, final IOperationTestRegister operationTestRegister) {
        this.operationsExecutorUseCase = operationsExecutorUseCase;
        this.operationTestRegister = operationTestRegister;
    }

    @PostMapping("/execute-operations")
    @ResponseStatus(HttpStatus.OK)
    public Object executeOperations(final @RequestBody TestData testData) {

        checkTestIsEmpty(testData);
        final TestId testId = TestId.builder().id(testData.getTestId()).build();
        final ClientId clientId = ClientId.builder().id(testData.getClientId()).build();
        final OperationsRequest operationsRequest = OperationsRequest.builder().testId(testId).clientId(clientId).testProperties(testData.getTestProperties()).build();
        final OperationsResult operationsResult = operationsExecutorUseCase.execute(operationsRequest);

        registerOperationsResults(operationsResult, testData.getClientId(), testId);

        return operationsResult;

    }

    private void registerOperationsResults(final OperationsResult operationsResult, final long client, final TestId testId) {
        for (OperationResult operationResult : operationsResult.getOperationResult()) {
            registerOperationResult(client, testId, operationResult);
        }
    }

    private void registerOperationResult(final long client, final TestId testId, final OperationResult operationResult) {
        final ClientId clientId = ClientId.builder().id(client).build();
        final OperationTestRegisterRequest operationTestRegisterRequest = OperationTestRegisterRequest.builder().testId(testId).clientId(clientId).operationResult(operationResult).clientId(clientId).build();
        operationTestRegister.registerOperationsResultsTest(operationTestRegisterRequest);
    }

    private void checkTestIsEmpty(@RequestBody final TestData testData) {
        if(testData.isEmpty()){
            throw new TestDataIncompleteException(NOT_VALIDS_TEST_VALUES);
        }
    }
}
