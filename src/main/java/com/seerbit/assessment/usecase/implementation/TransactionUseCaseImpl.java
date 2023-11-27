package com.seerbit.assessment.usecase.implementation;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.seerbit.assessment.entity.Transaction;
import com.seerbit.assessment.model.TransactionRequestJSON;
import com.seerbit.assessment.usecase.StatisticsProcessor;
import com.seerbit.assessment.usecase.TransactionUseCase;
import com.seerbit.assessment.usecase.exceptions.InvalidFieldException;
import com.seerbit.assessment.usecase.exceptions.NoContentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionUseCaseImpl implements TransactionUseCase {

    private final Gson gson;
    private final StatisticsProcessor statisticsProcessor;

    @Override
    public synchronized void createTransaction(TransactionRequestJSON requestJSON) {
        Transaction transaction = validateRequest(requestJSON);
        //update current statistics
        statisticsProcessor.updateStatistics(transaction.getAmount());

        List<Transaction> transactionList = getTransactions();
        if (transactionList == null) {
            transactionList = new ArrayList<>();
        }
        transactionList.add(transaction);
        createNewTransaction(transactionList);
    }

    @Override
    public void deleteTransactions() {
        List<Transaction> transactionList = new ArrayList<>();
        statisticsProcessor.deleteStatistics();
        createNewTransaction(transactionList);
    }

    @Override
    public void cleanUpOldStatistics() {
        List<Transaction> transactionList = getTransactions();

        if (transactionList != null) {
            int size = transactionList.size();
            ZonedDateTime thirtySecondsAgo = ZonedDateTime.now(ZoneOffset.UTC).minusSeconds(31);
            //filter transactions created 30 seconds ago
            transactionList = transactionList.stream().filter(transaction -> ZonedDateTime.parse(
                    transaction.getTimestamp()).isAfter(thirtySecondsAgo)).collect(Collectors.toList());

            //update statistics
            if (size != transactionList.size()) {
                List<BigDecimal> decimals = transactionList.stream().map(Transaction::getAmount).collect(Collectors.toList());
                statisticsProcessor.deleteStatistics();
                if (!decimals.isEmpty()) {
                    for (BigDecimal decimal : decimals) {
                        statisticsProcessor.updateStatistics(decimal);
                    }
                }
                createNewTransaction(transactionList);
            }
        }
    }

    private Transaction validateRequest(TransactionRequestJSON requestJSON) {
        BigDecimal amount;
        ZonedDateTime timestamp;
        try {
            amount = new BigDecimal(requestJSON.getAmount()).setScale(2, RoundingMode.HALF_UP);
            timestamp = ZonedDateTime.ofInstant(Instant.parse(requestJSON.getTimestamp()), ZoneOffset.UTC);
        } catch (Exception exception) {
            throw new InvalidFieldException("");
        }
        if (timestamp.isAfter(ZonedDateTime.now(ZoneOffset.UTC))) {
            throw new InvalidFieldException("");
        }
        if (timestamp.isBefore(ZonedDateTime.now(ZoneOffset.UTC).minusSeconds(30))) {
            throw new NoContentException();
        }
        return new Transaction(amount, timestamp.toString());
    }

    private void createNewTransaction(List<Transaction> transactionList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.json"))) {
            writer.write(gson.toJson(transactionList));
            writer.flush();
        } catch (IOException exception) {
            throw new RuntimeException();
        }
    }

    private List<Transaction> getTransactions() {
        List<Transaction> transactions;
        try {
            String transactionJson = Files.readString(Path.of("transactions.json"));
            transactions = gson.fromJson(transactionJson, new TypeToken<List<Transaction>>() {
            }.getType());
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return transactions;
    }

}
