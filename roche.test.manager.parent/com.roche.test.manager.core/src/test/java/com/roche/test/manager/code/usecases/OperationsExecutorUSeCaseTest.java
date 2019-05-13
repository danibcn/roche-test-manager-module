package com.roche.test.manager.code.usecases;

import com.roche.test.manager.core.dtos.*;
import com.roche.test.manager.core.entities.OperationId;
import com.roche.test.manager.core.entities.TestId;
import com.roche.test.manager.core.exceptions.OperationsExecuteNotFound;
import com.roche.test.manager.core.exceptions.TestInvalidException;
import com.roche.test.manager.core.exceptions.TestOperationClassNotFoundException;
import com.roche.test.manager.core.exceptions.TestValidatorClassNotFoundException;
import com.roche.test.manager.core.factories.FactoryOperationTestExecutor;
import com.roche.test.manager.core.factories.FactoryValidatorTest;
import com.roche.test.manager.core.repositories.IOperationsTestConfigRepository;
import com.roche.test.manager.core.usecases.OperationsExecutorUseCase;
import com.roche.test.manager.core.validators.IValidatorTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class OperationsExecutorUSeCaseTest {


    @Mock
    private FactoryOperationTestExecutor factoryOperationTestExecutor;

    @Mock
    private FactoryValidatorTest factoryValidatortest;

    @Mock
    private IOperationsTestConfigRepository operationsTestConfigRepository;

    private OperationsExecutorUseCase operationsExecutorUseCase;


    @Test
    public void given_test_when_validation_class_not_exists_then_throws_exception() {
        operationsExecutorUseCase = new OperationsExecutorUseCase(new FactoryValidatorTest(), factoryOperationTestExecutor, operationsTestConfigRepository);

        given(operationsTestConfigRepository.getOperationsTestConfig()).willReturn(initOperationsTestConfig());
        final List<TestProperty> testProperties = Arrays.asList(new TestProperty("density", "23"));
        final TestId testId = TestId.builder().id("Immunology").build();
        final OperationsRequest operationsRequest = OperationsRequest.builder().testProperties(testProperties).testId(testId).build();
        assertThatThrownBy(() -> operationsExecutorUseCase.execute(operationsRequest))
                .isInstanceOf(TestValidatorClassNotFoundException.class);
    }

    @Test
    public void given_test_when_operation_class_not_exists_then_throws_exception() {

        operationsExecutorUseCase = new OperationsExecutorUseCase(factoryValidatortest, new FactoryOperationTestExecutor(), operationsTestConfigRepository);

        given(factoryValidatortest.createValidator(any(TestId.class), any(List.class))).willReturn(new ImmunologyValidator());
        given(operationsTestConfigRepository.getOperationsTestConfig()).willReturn(initOperationsTestConfig());
        final List<TestProperty> testProperties = Arrays.asList(new TestProperty("density", "23"));
        final TestId testId = TestId.builder().id("Immunology").build();
        final OperationsRequest operationsRequest = OperationsRequest.builder().testProperties(testProperties).testId(testId).build();
        assertThatThrownBy(() -> operationsExecutorUseCase.execute(operationsRequest))
                .isInstanceOf(TestOperationClassNotFoundException.class);
    }

    @Test
    public void given_test_when_exists_validation_and_operations_then_return_result_valid() {
        operationsExecutorUseCase = new OperationsExecutorUseCase(new FactoryValidatorTest(), new FactoryOperationTestExecutor(), operationsTestConfigRepository);
        given(operationsTestConfigRepository.getOperationsTestConfig()).willReturn(initOperationsTestConfig());
        final List<TestProperty> testProperties = Arrays.asList(new TestProperty("density", "23"));
        final TestId testId = TestId.builder().id("Biochemistry").build();
        final OperationsRequest operationsRequest = OperationsRequest.builder().testProperties(testProperties).testId(testId).build();
        final OperationsResult operationsResult = operationsExecutorUseCase.execute(operationsRequest);
        Assert.assertEquals(operationsResult.getOperationResult().size() ,2);
    }

    @Test
    public void give_test_when_fails_the_validation_then_throws_exception(){
        operationsExecutorUseCase = new OperationsExecutorUseCase(new FactoryValidatorTest(), new FactoryOperationTestExecutor(), operationsTestConfigRepository);
        given(operationsTestConfigRepository.getOperationsTestConfig()).willReturn(initOperationsTestConfig());
        final List<TestProperty> testProperties = Arrays.asList(new TestProperty("density", "23"));
        final TestId testId = TestId.builder().id("Microbiology").build();
        final OperationsRequest operationsRequest = OperationsRequest.builder().testProperties(testProperties).testId(testId).build();
        assertThatThrownBy(() -> operationsExecutorUseCase.execute(operationsRequest))
                .isInstanceOf(TestInvalidException.class);
    }

    @Test
    public void give_test_when_not_exists_operations_to_apply_then_throws_exception(){
        operationsExecutorUseCase = new OperationsExecutorUseCase(new FactoryValidatorTest(), new FactoryOperationTestExecutor(), operationsTestConfigRepository);
        given(operationsTestConfigRepository.getOperationsTestConfig()).willReturn(initOperationsTestConfig());
        final List<TestProperty> testProperties = Arrays.asList(new TestProperty("density", "23"));
        final TestId testId = TestId.builder().id("Hematology").build();
        final OperationsRequest operationsRequest = OperationsRequest.builder().testProperties(testProperties).testId(testId).build();
        assertThatThrownBy(() -> operationsExecutorUseCase.execute(operationsRequest))
                .isInstanceOf(OperationsExecuteNotFound.class);
    }

    private static class ImmunologyValidator implements IValidatorTest{
        @Override
        public ValidationResult validate() {
            return ValidationResult.builder().valid(true).build();
        }
    }

    private OperationsTestConfig initOperationsTestConfig() {
        final Map<TestId, List<OperationId>> configTestsOperations = new HashMap<>();
        final TestId testId = TestId.builder().id("Biochemistry").build();
        final OperationId operationIdAverage = OperationId.builder().id("Average").build();
        final OperationId operationIdCount = OperationId.builder().id("count").build();
        final List<OperationId> operations = Collections.unmodifiableList(Arrays.asList(operationIdAverage, operationIdCount));
        configTestsOperations.put(testId, operations);
        configTestsOperations.putIfAbsent(TestId.builder().id("Immunology").build(), operations);
        configTestsOperations.putIfAbsent(TestId.builder().id("Microbiology").build(), operations);
        return new OperationsTestConfig(configTestsOperations);
    }


}
