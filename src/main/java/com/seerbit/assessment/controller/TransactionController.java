package com.seerbit.assessment.controller;

import com.seerbit.assessment.model.TransactionRequestJSON;
import com.seerbit.assessment.usecase.TransactionUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionUseCase transactionUseCase;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createTransaction(@Valid @RequestBody TransactionRequestJSON requestJSON) {
        transactionUseCase.createTransaction(requestJSON);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(value = "")
    public ResponseEntity<?> deleteTransactions() {
        transactionUseCase.deleteTransactions();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
