package com.roche.test.manager.controller;

import com.roche.test.manager.controller.exceptions.TestDataIncompleteException;
import com.roche.test.manager.core.dtos.OperationResult;
import com.roche.test.manager.core.dtos.OperationTestRegisterRequest;
import com.roche.test.manager.core.dtos.OperationsRequest;
import com.roche.test.manager.core.dtos.OperationsResult;
import com.roche.test.manager.core.entities.ClientId;
import com.roche.test.manager.core.entities.TestId;
import com.roche.test.manager.core.registers.IOperationTestRegister;
import com.roche.test.manager.core.usecases.IOperationsExecutorUseCase;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.util.NestedServletException;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class TestManagerModuleControllerTest {


    private static final String EXECUTE_OPERATIONS_ENDPOINT = "/v1/testmanager/execute-operations";
    private static final String NOT_VALIDS_TEST_VALUES = "Not valids test values";

    @Mock
    private IOperationsExecutorUseCase operationsExecutorUseCase;

    @Mock
    private IOperationTestRegister operationTestRegister;

    private MockMvcRequestSpecification mockMvcRequestSpecification;

    @Before
    public void setUp() {
        mockMvcRequestSpecification = given().standaloneSetup(new TestManagerModuleController(operationsExecutorUseCase, operationTestRegister));
    }

    @Test
    public void given_empty_test_when_test_type_is_empty_then_throws_exception() {

        String incompleteTestData = "{\"testProperties\":[{\"property\":\"density\",\"value\":\"5\"}],\"clientId\":32113}";

        MockMvcRequestSpecification mockMvcRequestSpecification = given().standaloneSetup(new TestManagerModuleController(operationsExecutorUseCase,
                operationTestRegister));

        assertThatThrownBy(() -> mockMvcRequestSpecification
                                    .contentType(JSON)
                                    .body(incompleteTestData)
                                    .when()
                                    .post(EXECUTE_OPERATIONS_ENDPOINT))
                .isInstanceOf(NestedServletException.class)
                .hasCause(new TestDataIncompleteException(NOT_VALIDS_TEST_VALUES));

    }

    @Test
    public void given_empty_test_when_properties_test_is_empty_then_throws_exception() {

        String incompleteTestData = "{\"testId\":\"Biochemistry\",\"clientId\":43432}";

        assertThatThrownBy(() -> mockMvcRequestSpecification
                                    .contentType(JSON)
                                    .body(incompleteTestData)
                                    .when()
                                    .post(EXECUTE_OPERATIONS_ENDPOINT))
                                    .isInstanceOf(NestedServletException.class)
                                    .hasCause(new TestDataIncompleteException(NOT_VALIDS_TEST_VALUES));
    }

    @Test
    public void given_empty_test_when_client_id_then_throws_exception() {

        String incompleteTestData = "{\"testId\":\"Biochemistry\",\"testProperties\":[{\"property\":\"density\",\"value\":\"34\"}]}";

        assertThatThrownBy(() -> mockMvcRequestSpecification
                                    .contentType(JSON)
                                    .body(incompleteTestData)
                                    .when()
                                    .post(EXECUTE_OPERATIONS_ENDPOINT))
                                    .isInstanceOf(NestedServletException.class)
                                    .hasCause(new TestDataIncompleteException(NOT_VALIDS_TEST_VALUES));
    }

    @Test
    public void given_valid_test_data_when_execute_operations_then_result_is_valid(){

        final List<OperationResult> operationsResultList = new ArrayList<>();
        OperationResult operationResult = OperationResult.builder().message("count").value(32).build();
        operationsResultList.add(operationResult);
        operationResult = OperationResult.builder().message("count").value(32).build();
        operationsResultList.add(operationResult);
        final TestId testId = TestId.builder().id("Biochemistry").build();
        final ClientId clientId = ClientId.builder().id(232).build();

        final OperationsResult operationsResult = new OperationsResult(testId, clientId, operationsResultList);

        given(operationsExecutorUseCase.execute(any(OperationsRequest.class))).willReturn(operationsResult);

        String testData = "{\"testId\":\"Biologia\",\"testProperties\":[{\"property\":\"1\",\"value\":\"1\"}],\"clientId\":1}";

        MockMvcResponse mockMvcResponse = mockMvcRequestSpecification
                                            .contentType(JSON)
                                            .body(testData)
                                            .when()
                                            .post(EXECUTE_OPERATIONS_ENDPOINT);

        mockMvcResponse
                .then()
                .statusCode(200)
                .contentType(JSON)
                .body("operationResult", response -> Matchers.notNullValue());

        BDDMockito.verify(operationTestRegister, times(2)).registerOperationsResultsTest(any(OperationTestRegisterRequest.class));

    }

}
