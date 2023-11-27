package com.seerbit.assessment.usecase.implementation;

import com.google.gson.Gson;
import com.seerbit.assessment.model.TransactionRequestJSON;
import com.seerbit.assessment.usecase.StatisticsProcessor;
import com.seerbit.assessment.usecase.exceptions.InvalidFieldException;
import com.seerbit.assessment.usecase.exceptions.NoContentException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TransactionUseCaseImplTest {

    @Mock
    private StatisticsProcessor mockStatisticsProcessor;

    private TransactionUseCaseImpl transactionUseCaseImplUnderTest;

    @BeforeEach
    void setUp() {
        transactionUseCaseImplUnderTest = new TransactionUseCaseImpl(new Gson(), mockStatisticsProcessor);
    }

    @Test
    void testCreateTransaction() {
        // Setup
        final TransactionRequestJSON requestJSON = new TransactionRequestJSON();
        requestJSON.setAmount("120.897");
        ZonedDateTime oneSecondAgo = ZonedDateTime.now(ZoneOffset.UTC).minusSeconds(1);
        requestJSON.setTimestamp(oneSecondAgo.toString());

        // Run the test
        transactionUseCaseImplUnderTest.createTransaction(requestJSON);

        // Verify the results
        verify(mockStatisticsProcessor).updateStatistics(new BigDecimal("120.90"));
    }

    @Test
    void testCreateTransactionThrowsNoContentException() {
        // Setup
        final TransactionRequestJSON requestJSON = new TransactionRequestJSON();
        requestJSON.setAmount("120.897");
        ZonedDateTime oneSecondAgo = ZonedDateTime.now(ZoneOffset.UTC).minusSeconds(31);
        requestJSON.setTimestamp(oneSecondAgo.toString());

        // Verify the results
        Assertions.assertThrows(NoContentException.class, () -> transactionUseCaseImplUnderTest.createTransaction(requestJSON));
    }

    @Test
    void testCreateTransactionThrowsInvalidFieldException() {
        // Setup
        final TransactionRequestJSON requestJSON = new TransactionRequestJSON();
        requestJSON.setAmount("12nm0.897");
        ZonedDateTime oneSecondAgo = ZonedDateTime.now(ZoneOffset.UTC).plusSeconds(31);
        requestJSON.setTimestamp(oneSecondAgo.toString());

        // Verify the results
        Assertions.assertThrows(InvalidFieldException.class, () -> transactionUseCaseImplUnderTest.createTransaction(requestJSON));
    }

    @Test
    void testDeleteTransactions() {
        // Run the test
        transactionUseCaseImplUnderTest.deleteTransactions();

        // Verify the results
        verify(mockStatisticsProcessor).deleteStatistics();
    }

    @Test
    void testCleanUpOldStatistics() {
        // Run the test
        transactionUseCaseImplUnderTest.cleanUpOldStatistics();
    }
}
