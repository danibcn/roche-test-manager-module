package com.roche.test.manager.core.usecases;

import com.roche.test.manager.core.dtos.OperationsRequest;
import com.roche.test.manager.core.dtos.OperationsResult;

public interface IOperationsExecutorUseCase {

    OperationsResult execute(final OperationsRequest operationsRequest);
}
