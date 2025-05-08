package com.wayon.SmartTransfer.mapper;

import com.wayon.SmartTransfer.dto.transfers.CreateTransfersRequest;
import com.wayon.SmartTransfer.dto.transfers.TransfersResponse;
import com.wayon.SmartTransfer.entity.transfers.Transfers;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface ITransfersMapper {

    ITransfersMapper INSTANCE = Mappers.getMapper(ITransfersMapper.class);

    default Page<TransfersResponse> TransferToPageTransferResponse(Page<Transfers> transferPage) {
        List<TransfersResponse> transfersResponseList = transferPage.getContent().stream()
                .map(this::TransfersToTransfersResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(transfersResponseList, transferPage.getPageable(), transferPage.getTotalElements());
    }

    Transfers CreateTransfersRequestToTransfer(CreateTransfersRequest request);

    TransfersResponse TransfersToTransfersResponse(Transfers transfers);
}
