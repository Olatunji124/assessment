package com.seerbit.assessment.entity;

import com.seerbit.assessment.usecase.StatisticsProcessor;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
public class Statistics {
    private String sum;
    private String average;
    private String max;
    private String min;
    private Long count;

    public Statistics(StatisticsProcessor processor){
        this.sum = processor.getSum().toPlainString();
        this.count = processor.getCount();
        this.max = processor.getMax().toPlainString();
        this.min = processor.getMin().toPlainString();
        this.average = count > 0 ? processor.getSum().divide(new BigDecimal(count), 2, RoundingMode.HALF_UP).toPlainString() : "0.00";
    }
}
