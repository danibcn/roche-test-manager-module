package com.roche.test.manager.core.dtos;

import com.roche.test.manager.core.entities.ClientId;
import com.roche.test.manager.core.entities.TestId;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

@Getter
@Builder
public class OperationsRequest {

    private final TestId testId;
    private final ClientId clientId;
    private final List<TestProperty> testProperties;

    public List<TestProperty> getTestProperties() {
        return unmodifiableList(new ArrayList<>(testProperties));
    }


}
