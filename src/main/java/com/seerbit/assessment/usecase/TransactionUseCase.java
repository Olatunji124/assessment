package com.seerbit.assessment.usecase;

import com.seerbit.assessment.model.TransactionRequestJSON;

public interface TransactionUseCase {
    void createTransaction(TransactionRequestJSON requestJSON);
    void deleteTransactions();
    void cleanUpOldStatistics();
}
