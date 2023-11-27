package com.seerbit.assessment.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TransactionStatisticsResponse {
    private String sum;
    private String avg;
    private String max;
    private String min;
    private long count;
}
