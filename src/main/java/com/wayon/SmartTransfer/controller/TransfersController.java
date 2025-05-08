package com.wayon.SmartTransfer.controller;

import com.wayon.SmartTransfer.dto.error.ErrorResponse;
import com.wayon.SmartTransfer.dto.transfers.CreateTransfersRequest;
import com.wayon.SmartTransfer.dto.transfers.TransfersResponse;
import com.wayon.SmartTransfer.exceptions.InvalidOperatorException;
import com.wayon.SmartTransfer.service.TokenService;
import com.wayon.SmartTransfer.service.TransfersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/transfers")
public class TransfersController {

    @Autowired
    private TransfersService transfersService;

    private final TokenService tokenService;

    public TransfersController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<?> createTransfer(@Valid @RequestBody CreateTransfersRequest createTransfersRequest,
                                            UriComponentsBuilder uriComponentsBuilder,
                                            HttpServletRequest request) {
        try {
            var token = request.getHeader("Authorization").split(" ");

            var id = transfersService.createTransfer(createTransfersRequest, tokenService.getUserId(token[1]));

            var uri = uriComponentsBuilder.path("/transfers/{id}").buildAndExpand(id).toUri();
            return ResponseEntity.created(uri).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> getTransferById(@PathVariable String uuid, HttpServletRequest request) {
        try {
            var token = request.getHeader("Authorization").split(" ");

            var response = transfersService.getTransferById(uuid, tokenService.getUserId(token[1]));

            return ResponseEntity.ok(response);
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidOperatorException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(e.getMessage()));
        }
    }

    @GetMapping
    public Page<TransfersResponse> searchByLoggedUser(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam MultiValueMap<String, String> params,
            HttpServletRequest request
    ) {
        var token = request.getHeader("Authorization").split(" ");

        params.set("createdBy", tokenService.getUserId(token[1]));
        return transfersService.search(pageable, params);
    }
}
