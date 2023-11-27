package com.seerbit.assessment.usecase;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Getter
public class StatisticsProcessor {

    private BigDecimal sum;
    private BigDecimal max;
    private BigDecimal min;
    private long count;

    public StatisticsProcessor() {

    }

    public StatisticsProcessor(BigDecimal sum, BigDecimal max, BigDecimal min, long count) {
        this.sum = sum;
        this.max = max;
        this.min = min;
        this.count = count;
    }

    public synchronized void updateStatistics(BigDecimal amount) {
        if (count == 0) {
            sum = amount;
            max = amount;
            min = amount;
            count = 1;
        } else {
            sum = sum.add(amount);
            if (min.compareTo(amount) > 0) {
                min = amount;
            }
            if (max.compareTo(amount) < 0) {
                max = amount;
            }
            count++;
        }
    }

    public void deleteStatistics() {
        sum = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        max = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        min = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        count= 0;
    }
}
