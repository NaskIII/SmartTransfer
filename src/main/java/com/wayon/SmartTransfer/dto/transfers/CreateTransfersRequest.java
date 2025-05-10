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

    @Pattern(regexp = "^[A-Za-z0-9]{10}$", message = "O número da conta precisa possuir 10 dígitos!")
    private String destinationAccount;

    @NotNull(message = "O campo Valor é obrigatório.")
    @DecimalMin(value = "0.01", inclusive = true, message = "O campo valor precisa possuir um valor maior que 0.")
    private BigDecimal amount;

    @NotNull(message = "A data de transferência é obrigatória")
    @FutureOrPresent(message = "A data de transferência não pode ser anterior ao dia de hoje.")
    private LocalDate transferDate;

}
