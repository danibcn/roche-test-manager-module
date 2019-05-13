package com.roche.test.manager.core.factories;

import com.roche.test.manager.core.dtos.TestProperty;
import com.roche.test.manager.core.entities.TestId;
import com.roche.test.manager.core.exceptions.TestValidatorClassNotFoundException;
import com.roche.test.manager.core.validators.IValidatorTest;
import org.springframework.util.StringUtils;

import java.lang.reflect.Constructor;
import java.util.List;

@SuppressWarnings("unchecked")
public class FactoryValidatorTest {


    private static final String PACKAGE_VALIDATIONS_BASE = "com.roche.test.manager.core.validators.";
    private static final String VALIDATOR_SUFFIX = "Validator";

    public IValidatorTest createValidator(final TestId testId, final List<TestProperty> testProperties) {

        final String test = StringUtils.capitalize(testId.getId().toLowerCase());
        IValidatorTest validatorTest;
        String className = "";
        try {
            className = PACKAGE_VALIDATIONS_BASE +test+ VALIDATOR_SUFFIX;
            final Class c = Class.forName(className);
            final Constructor<?> cons = c.getConstructor(List.class);
            validatorTest = (IValidatorTest) cons.newInstance(testProperties);
        } catch (Exception e) {
           throw new TestValidatorClassNotFoundException("Class: " +className+" not found.");
        }
        return validatorTest;
    }
}
