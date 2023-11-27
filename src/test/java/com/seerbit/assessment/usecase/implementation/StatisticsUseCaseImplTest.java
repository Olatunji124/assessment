package com.seerbit.assessment.usecase.implementation;

import com.seerbit.assessment.model.TransactionStatisticsResponse;
import com.seerbit.assessment.usecase.StatisticsProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class StatisticsUseCaseImplTest {

    @Mock
    private StatisticsProcessor mockStatisticsProcessor;

    private StatisticsUseCaseImpl statisticsUseCaseImplUnderTest;

    @BeforeEach
    void setUp() {
        statisticsUseCaseImplUnderTest = new StatisticsUseCaseImpl(mockStatisticsProcessor);
    }

    @Test
    void testGetTransactionStatistics() {
        // Setup
        mockStatisticsProcessor = new StatisticsProcessor(new BigDecimal("1000"), new BigDecimal("600"), new BigDecimal("100"), 3);
        statisticsUseCaseImplUnderTest = new StatisticsUseCaseImpl(mockStatisticsProcessor);
        // Run the test
        final TransactionStatisticsResponse result = statisticsUseCaseImplUnderTest.getTransactionStatistics();

        // Verify the results
        assertThat(result.getCount()).isEqualTo(3);
    }
}
