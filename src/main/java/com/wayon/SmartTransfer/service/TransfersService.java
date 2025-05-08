package com.wayon.SmartTransfer.service;

import com.wayon.SmartTransfer.dto.transfers.CreateTransfersRequest;
import com.wayon.SmartTransfer.dto.transfers.TransfersResponse;
import com.wayon.SmartTransfer.entity.transfers.Transfers;
import com.wayon.SmartTransfer.exceptions.InvalidOperatorException;
import com.wayon.SmartTransfer.mapper.ITransfersMapper;
import com.wayon.SmartTransfer.repository.ITransfersRepository;
import com.wayon.SmartTransfer.repository.IUserRepository;
import com.wayon.SmartTransfer.specifications.transfers.TransferSpecifications;
import com.wayon.SmartTransfer.specifications.transfers.TransfersSearchCriteria;
import com.wayon.SmartTransfer.utils.FeeRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class TransfersService {

    @Autowired
    private ITransfersRepository transfersRepository;

    @Autowired
    IUserRepository userRepository;

    @Transactional
    public String createTransfer(CreateTransfersRequest transfersRequest, String userId) throws ChangeSetPersister.NotFoundException {
        var user = userRepository.findById(userId).orElseThrow(ChangeSetPersister.NotFoundException::new);

        LocalDate today = LocalDate.now();

        int daysDifference = FeeRange.calculateDaysDifference(today, transfersRequest.getTransferDate());

        FeeRange feeRange = FeeRange.fromDays(daysDifference);
        BigDecimal fee = feeRange.calculateFee(transfersRequest.getAmount());

        Transfers transfer = ITransfersMapper.INSTANCE.CreateTransfersRequestToTransfer(transfersRequest);
        transfer.setSourceAccount(user.getSourceAccount());
        transfer.setFee(fee);

        Transfers savedTransfer = transfersRepository.save(transfer);

        return savedTransfer.getTransferId();
    }

    public TransfersResponse getTransferById(String id, String userId) throws ChangeSetPersister.NotFoundException, InvalidOperatorException {
        Transfers transfers = transfersRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);

        if (!transfers.getCreatedBy().equals(userId)) {
            throw new InvalidOperatorException("Only personal transfers can be viewed.");
        }

        return ITransfersMapper.INSTANCE.TransfersToTransfersResponse(transfers);
    }

    public Page<TransfersResponse> search(Pageable pageable,
                                          MultiValueMap<String, String> params) {
        var searchCriteria = TransfersSearchCriteria.builder()
                .transferId(params.getFirst("transferId"))
                .sourceAccount(params.getFirst("sourceAccount"))
                .destinationAccount(params.getFirst("destinationAccount"))
                .createdBy(params.getFirst("createdBy"))
                .build();

        var specification = TransferSpecifications.search(searchCriteria);

        var transfers = transfersRepository.findAll(specification, pageable);

        return ITransfersMapper.INSTANCE.TransferToPageTransferResponse(transfers);
    }
}
