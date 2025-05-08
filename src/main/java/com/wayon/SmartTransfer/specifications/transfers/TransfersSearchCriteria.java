package com.wayon.SmartTransfer.specifications.transfers;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TransfersSearchCriteria {

    private String transferId;

    private String sourceAccount;

    private String destinationAccount;

    private String createdBy;
}
