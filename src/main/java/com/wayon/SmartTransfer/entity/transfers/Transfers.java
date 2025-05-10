package com.wayon.SmartTransfer.entity.transfers;

import com.wayon.SmartTransfer.entity.Auditable;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "transfers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transfers extends Auditable {

    @Id
    @Column(updatable = false, unique = true, nullable = false)
    private String transferId = UUID.randomUUID().toString();

    @Column(name = "source_account", nullable = false, length = 10)
    private String sourceAccount;

    @Column(name = "destination_account", nullable = false, length = 10)
    private String destinationAccount;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal fee;

    @Column(name = "transfer_date", nullable = false)
    private LocalDate transferDate;

    @Column(name = "schedule_date", nullable = false)
    private LocalDate scheduleDate = LocalDate.now();
    
}

