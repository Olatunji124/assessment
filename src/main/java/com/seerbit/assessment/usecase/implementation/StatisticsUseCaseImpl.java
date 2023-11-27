package com.seerbit.assessment.usecase.implementation;

import com.seerbit.assessment.entity.Statistics;
import com.seerbit.assessment.model.TransactionStatisticsResponse;
import com.seerbit.assessment.usecase.StatisticsProcessor;
import com.seerbit.assessment.usecase.StatisticsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsUseCaseImpl implements StatisticsUseCase {

    private final StatisticsProcessor statisticsProcessor;

    @Override
    public TransactionStatisticsResponse getTransactionStatistics() {
        Statistics statistics = new Statistics(statisticsProcessor);
        return TransactionStatisticsResponse.builder()
                .sum(statistics.getSum())
                .max(statistics.getMax())
                .min(statistics.getMin())
                .avg(statistics.getAverage())
                .count(statistics.getCount())
                .build();
    }
}
