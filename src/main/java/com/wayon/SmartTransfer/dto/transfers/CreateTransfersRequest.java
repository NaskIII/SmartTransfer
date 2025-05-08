package com.wayon.SmartTransfer.dto.transfers;

import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTransfersRequest {

    @Pattern(regexp = "^[A-Za-z0-9]{10}$", message = "Account number must be exactly 10 alphanumeric characters")
    private String destinationAccount;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", inclusive = true, message = "The amount value should be greater than 0.")
    private BigDecimal amount;

    @NotNull(message = "Transfer date is required")
    @FutureOrPresent(message = "Transfer date must not be in the past")
    private LocalDate transferDate;

}
