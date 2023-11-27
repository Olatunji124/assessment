package com.seerbit.assessment.controller;

import com.seerbit.assessment.model.TransactionStatisticsResponse;
import com.seerbit.assessment.usecase.StatisticsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsUseCase statisticsUseCase;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionStatisticsResponse> getStatistics() {
        TransactionStatisticsResponse statisticsResponse = statisticsUseCase.getTransactionStatistics();
        return new ResponseEntity<>(statisticsResponse, HttpStatus.CREATED);
    }
}
