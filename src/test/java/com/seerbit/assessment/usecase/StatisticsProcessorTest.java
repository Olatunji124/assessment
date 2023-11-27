package com.seerbit.assessment.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class StatisticsProcessorTest {

    private StatisticsProcessor statisticsProcessorUnderTest;

    @BeforeEach
    void setUp() {
        statisticsProcessorUnderTest = new StatisticsProcessor();
    }

    @Test
    void testUpdateStatistics() {
        // Run the test
        statisticsProcessorUnderTest.updateStatistics(new BigDecimal("120.00"));
    }

    @Test
    void testDeleteStatistics() {
        // Run the test
        statisticsProcessorUnderTest.deleteStatistics();
    }
}
