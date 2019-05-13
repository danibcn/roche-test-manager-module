package com.roche.test.manager.core.validators;

import com.roche.test.manager.core.dtos.TestProperty;
import com.roche.test.manager.core.dtos.ValidationResult;

import java.util.List;

public class BiochemistryValidator extends  AbstractValidation implements IValidatorTest {

    public BiochemistryValidator(final List<TestProperty> testProperties) {
        this.testProperties = testProperties;
    }

    @Override
    public ValidationResult validate() {
        return ValidationResult.builder().valid(true).message("Test properties valid").build();
    }
}
