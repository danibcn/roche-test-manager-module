package com.roche.test.manager.core.factories;

import com.roche.test.manager.core.dtos.TestProperty;
import com.roche.test.manager.core.entities.OperationId;
import com.roche.test.manager.core.entities.TestId;
import com.roche.test.manager.core.exceptions.TestOperationClassNotFoundException;
import com.roche.test.manager.core.operations.IOperationTestExecutor;
import org.springframework.util.StringUtils;

import java.lang.reflect.Constructor;
import java.util.List;

@SuppressWarnings("unchecked")
public class FactoryOperationTestExecutor {

    private static final String OPERATIONS_PACKAGE_BASE = "com.roche.test.manager.core.operations.";

    public IOperationTestExecutor createOperationExecutor(final TestId testId, final List<TestProperty> testProperties, final OperationId operationId) {

            final String test = StringUtils.capitalize(testId.getId().toLowerCase());
            final String operation = StringUtils.capitalize(operationId.getId().toLowerCase());
            IOperationTestExecutor operationTestExecutor;
            String className = "";
            try {
                className = OPERATIONS_PACKAGE_BASE +test+operation;
                final Class c = Class.forName(className);
                final Constructor<?> cons = c.getConstructor(List.class);
                operationTestExecutor = (IOperationTestExecutor) cons.newInstance(testProperties);
            } catch (Exception e) {
                throw new TestOperationClassNotFoundException("Class: "+className+" not found.");
            }
            return operationTestExecutor;
        }
}
