package com.wayon.SmartTransfer.specifications.transfers;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class TransfersSearchCriteria {

    private String transferId;

    private String sourceAccount;

    private String destinationAccount;

    private String createdBy;

    private LocalDate scheduleDate;

    private LocalDate transferDate;
}
