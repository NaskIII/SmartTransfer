package com.wayon.SmartTransfer.repository;

import com.wayon.SmartTransfer.entity.transfers.Transfers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransfersRepository extends JpaRepository<Transfers, String>, JpaSpecificationExecutor<Transfers> {
}
