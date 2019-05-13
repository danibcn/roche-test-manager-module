package com.roche.test.manager.core.validators;

import com.roche.test.manager.core.dtos.TestProperty;
import com.roche.test.manager.core.dtos.ValidationResult;

import java.util.List;

public class MicrobiologyValidator extends  AbstractValidation implements IValidatorTest {

    public MicrobiologyValidator(final List<TestProperty> testProperties) {
        this.testProperties = testProperties;
    }

    @Override
    public ValidationResult validate() {
        return ValidationResult.builder().valid(false).message("Test properties fails: The properties are inconsistents.").build();
    }
}
