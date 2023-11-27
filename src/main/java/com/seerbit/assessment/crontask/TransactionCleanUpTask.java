package com.seerbit.assessment.crontask;

import com.seerbit.assessment.usecase.TransactionUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TransactionCleanUpTask {

    private final TransactionUseCase transactionUseCase;

    private static final String FIXED_DELAY_TIME = "1000";

    @Async
    @Scheduled(fixedDelayString = FIXED_DELAY_TIME)
    public synchronized void cleanUpStatisticsOlderThanThirtySeconds() {
        transactionUseCase.cleanUpOldStatistics();
    }
}
