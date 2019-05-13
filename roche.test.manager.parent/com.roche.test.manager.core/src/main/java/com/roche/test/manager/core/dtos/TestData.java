package com.roche.test.manager.core.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TestData {

    private String testId;
    private List<TestProperty> testProperties;
    private long clientId;

    public List<TestProperty> getTestProperties() {
        return Collections.unmodifiableList(new ArrayList<>(testProperties));
    }

    public boolean isEmpty() {
        return CollectionUtils.isEmpty(testProperties) || testId == null || testId.length() == 0 || clientId == 0;
    }



}
