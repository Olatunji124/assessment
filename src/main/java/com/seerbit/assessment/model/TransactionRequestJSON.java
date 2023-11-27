package com.seerbit.assessment.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionRequestJSON {
    @NotBlank
    private String amount;
    @NotBlank
    private String timestamp;
}
