package com.wayon.SmartTransfer.dto.transfers;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransfersResponse {

    private String transferId;
    private String sourceAccount;
    private String destinationAccount;
    private BigDecimal amount;
    private BigDecimal fee;
    private LocalDate transferDate;
    private LocalDate scheduleDate;
}
