package com.roche.test.manager;

import com.roche.test.manager.core.factories.FactoryOperationTestExecutor;
import com.roche.test.manager.core.factories.FactoryValidatorTest;
import com.roche.test.manager.core.registers.IOperationTestRegister;
import com.roche.test.manager.core.registers.OperationTestLogger;
import com.roche.test.manager.core.repositories.IOperationsTestConfigRepository;
import com.roche.test.manager.core.usecases.OperationsExecutorUseCase;
import com.roche.test.manager.repository.OperationsTestConfigMap;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean(name="operationTestRegister")
    public IOperationTestRegister operationTestRegister() {
        return new OperationTestLogger();
    }

    @Bean(name="operationsTestConfigMap")
    public OperationsTestConfigMap operationsTestConfigMap() {
        return new OperationsTestConfigMap();
    }

    @Bean(name="factoryOperationTestExecutor")
    public FactoryOperationTestExecutor factoryOperationTestExecutor() {
        return new FactoryOperationTestExecutor();
    }

    @Bean(name="factoryValidatorTest")
    public FactoryValidatorTest factoryValidatorTest() {
        return new FactoryValidatorTest();
    }


    @Bean(name="OperationsExecutorUseCase")
    public OperationsExecutorUseCase operationsExecutorUseCase(
            @Qualifier("factoryValidatorTest") final FactoryValidatorTest factoryValidatorTest,
            @Qualifier("factoryOperationTestExecutor") final FactoryOperationTestExecutor factoryOperationTestExecutor,
            @Qualifier("operationsTestConfigMap") final IOperationsTestConfigRepository operationsTestConfigRepository) {
        return new OperationsExecutorUseCase(factoryValidatorTest, factoryOperationTestExecutor, operationsTestConfigRepository);
    }


}
