package openg2p.vendor.items.controller;


import openg2p.vendor.items.dto.ItemTransactionDto;
import openg2p.vendor.items.service.ItemTransactionService;
import openg2p.vendor.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/transaction")
public class ItemTransactionController {

    @Autowired
    ItemTransactionService itemTransactionService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Object>> registerSuperUser(
            @RequestBody @Valid ItemTransactionDto itemTransactionDto) {

        ItemTransactionDto itemTransactionDtoResponse = itemTransactionService.addTransaction(itemTransactionDto);

        ApiResponse<Object> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                itemTransactionDto.getId()==null ? "Transaction Created":"Transaction Updated",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")),
                itemTransactionDtoResponse
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status")
    public ResponseEntity<ApiResponse<Object>> getVendorBeneficiaryTransaction(@RequestParam Long vendorId,
    @RequestParam String beneficiaryId, @RequestParam String itemCode) {
        ItemTransactionDto itemTransactionDto = itemTransactionService.getVendorBeneficiaryTransaction(vendorId, beneficiaryId,itemCode);

        ApiResponse<Object> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Transaction Found",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")),
                itemTransactionDto
        );
        return ResponseEntity.ok(response);
    }
}
