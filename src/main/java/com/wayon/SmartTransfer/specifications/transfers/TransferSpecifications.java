package com.wayon.SmartTransfer.specifications.transfers;

import com.wayon.SmartTransfer.entity.transfers.Transfers;
import com.wayon.SmartTransfer.entity.transfers.Transfers_;
import org.springframework.data.jpa.domain.Specification;


public class TransferSpecifications {

    public static Specification<Transfers> search(TransfersSearchCriteria transfersSearchCriteria) {
        return searchByTransferId(transfersSearchCriteria.getTransferId())
                .and(searchBySourceAccount(transfersSearchCriteria.getSourceAccount()))
                .and(searchByDestinationAccount(transfersSearchCriteria.getDestinationAccount()))
                .and(searchByCreatedBy(transfersSearchCriteria.getCreatedBy()));
    }

    private static Specification<Transfers> searchByTransferId(String transferId) {
        return (root, query, criteriaBuilder) -> {
            if (transferId == null) return null;

            return criteriaBuilder.equal(root.get(Transfers_.transferId), transferId);
        };
    }

    private static Specification<Transfers> searchBySourceAccount(String sourceAccount) {
        return (root, query, criteriaBuilder) -> {
            if (sourceAccount == null) return null;

            return criteriaBuilder.equal(root.get(Transfers_.sourceAccount.getName()), sourceAccount);
        };
    }

    private static Specification<Transfers> searchByDestinationAccount(String destinationAccount) {
        return (root, query, criteriaBuilder) -> {
            if (destinationAccount == null) return null;

            return criteriaBuilder.equal(root.get(Transfers_.destinationAccount.getName()), destinationAccount);
        };
    }

    private static Specification<Transfers> searchByCreatedBy(String createdBy) {
        return (root, query, criteriaBuilder) -> {
            if (createdBy == null) return null;

            return criteriaBuilder.equal(root.get(Transfers_.createdBy.getName()), createdBy);
        };
    }
}
