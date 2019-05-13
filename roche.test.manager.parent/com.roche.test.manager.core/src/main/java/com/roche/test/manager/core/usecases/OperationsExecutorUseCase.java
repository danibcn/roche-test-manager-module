package com.roche.test.manager.core.usecases;

import com.roche.test.manager.core.dtos.*;
import com.roche.test.manager.core.entities.OperationId;
import com.roche.test.manager.core.entities.TestId;
import com.roche.test.manager.core.exceptions.OperationsExecuteNotFound;
import com.roche.test.manager.core.exceptions.TestInvalidException;
import com.roche.test.manager.core.factories.FactoryOperationTestExecutor;
import com.roche.test.manager.core.factories.FactoryValidatorTest;
import com.roche.test.manager.core.operations.IOperationTestExecutor;
import com.roche.test.manager.core.repositories.IOperationsTestConfigRepository;
import com.roche.test.manager.core.validators.IValidatorTest;

import java.util.ArrayList;
import java.util.List;

public class OperationsExecutorUseCase implements IOperationsExecutorUseCase {

    public static final String NOT_FOUND_OPERATIONS_TO_EXECUTE_IN_THE_TEST = "Not found operations to execute in the test";
    private final FactoryOperationTestExecutor factoryOperationTestExecutor;
    private final FactoryValidatorTest factoryValidatorTest;
    private final IOperationsTestConfigRepository operationsTestConfigRepository;

    public OperationsExecutorUseCase(final FactoryValidatorTest factoryValidatorTest,
                                     final FactoryOperationTestExecutor factoryOperationTestExecutor,
                                     final IOperationsTestConfigRepository operationsTestConfigRepository) {
        this.factoryValidatorTest = factoryValidatorTest;
        this.factoryOperationTestExecutor = factoryOperationTestExecutor;
        this.operationsTestConfigRepository = operationsTestConfigRepository;
    }

    public OperationsResult execute(final OperationsRequest operationsRequest) {

        final OperationsTestConfig operationsTestConfig = operationsTestConfigRepository.getOperationsTestConfig();

        final List<OperationId> operations = operationsTestConfig.getOperations(operationsRequest.getTestId());
        checkOperationsExists(operations);
        return new OperationsResult(operationsRequest.getTestId(), operationsRequest.getClientId(), processOperations(operationsRequest, operations));
    }

    private List<OperationResult> processOperations(final OperationsRequest operationsRequest, final List<OperationId> operations) {
        final List<OperationResult> operationsResults = new ArrayList<>();
        for (OperationId operation : operations) {
            final TestId testId = operationsRequest.getTestId();
            final List<TestProperty> testProperties = operationsRequest.getTestProperties();

            validateTest(testId, testProperties);
            executeOperation(operationsResults, operation, testId, testProperties);
        }
        return operationsResults;
    }

    private void checkOperationsExists(final List<OperationId> operations) {
        if(operations.isEmpty()){
            throw new OperationsExecuteNotFound(NOT_FOUND_OPERATIONS_TO_EXECUTE_IN_THE_TEST);
        }
    }

    private void executeOperation(final List<OperationResult> operationsResults, final OperationId operation, final TestId testId, final List<TestProperty> testProperties) {
        IOperationTestExecutor operationTestExecutor = factoryOperationTestExecutor.createOperationExecutor(testId, testProperties, operation);
        operationsResults.add(operationTestExecutor.execute());
    }

    private void validateTest(final TestId testId, final List<TestProperty> testProperties) {
        IValidatorTest validator = factoryValidatorTest.createValidator(testId, testProperties);
        final ValidationResult validate = validator.validate();
        if(!validate.isValid()) {
            throw new TestInvalidException(validate.getMessage());
        }
    }
}
