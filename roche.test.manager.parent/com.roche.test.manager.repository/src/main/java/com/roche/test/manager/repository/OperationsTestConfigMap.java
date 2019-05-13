package com.roche.test.manager.repository;

import com.roche.test.manager.core.dtos.OperationsTestConfig;
import com.roche.test.manager.core.entities.OperationId;
import com.roche.test.manager.core.entities.TestId;
import com.roche.test.manager.core.repositories.IOperationsTestConfigRepository;

import java.util.*;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

public class OperationsTestConfigMap implements IOperationsTestConfigRepository {


    public OperationsTestConfig getOperationsTestConfig() {

        final Map<TestId, List<OperationId>> configTestsOperations = new HashMap<>();

        final TestId testId = TestId.builder().id("Biochemistry").build();
        final OperationId operationIdAverage = OperationId.builder().id("Average").build();
        final OperationId operationIdCount = OperationId.builder().id("count").build();
        final List<OperationId> operations = unmodifiableList(Arrays.asList(operationIdAverage, operationIdCount));

        configTestsOperations.put(testId, operations);
        configTestsOperations.putIfAbsent(TestId.builder().id("Immunology").build(), operations);
        configTestsOperations.putIfAbsent(TestId.builder().id("Microbiology").build(), operations);
        configTestsOperations.putIfAbsent(TestId.builder().id("Hematology").build(), operations);
        configTestsOperations.putIfAbsent(TestId.builder().id("Genetic").build(), emptyList());


        return new OperationsTestConfig(configTestsOperations);
    }
}
