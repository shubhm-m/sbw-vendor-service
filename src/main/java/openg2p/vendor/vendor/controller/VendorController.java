package openg2p.vendor.vendor.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import openg2p.vendor.util.ApiResponse;
import openg2p.vendor.vendor.dto.SupervisorRegistrationDTO;
import openg2p.vendor.vendor.dto.VendorRegistrationDTO;
import openg2p.vendor.vendor.service.VendorUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {

    private final VendorUserService vendorUserService;

    @Autowired
    public VendorController(VendorUserService vendorUserService) {
        this.vendorUserService = vendorUserService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Object>> registerVendor(
            @Valid @RequestPart("registrationDTO") String registrationDTO,
            @RequestPart(value = "vatProof", required = false) MultipartFile vatProof,
            @RequestPart(value = "taxProof", required = false) MultipartFile taxProof,
            @RequestPart(value = "signature", required = false) MultipartFile signature) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        VendorRegistrationDTO vendorRegistrationDTO = objectMapper.readValue(registrationDTO, VendorRegistrationDTO.class);
        vendorRegistrationDTO.setTaxProof(taxProof);
        vendorRegistrationDTO.setSignature(signature);
        vendorRegistrationDTO.setVatProof(vatProof);
        HashMap<String, Long> responseMap = vendorUserService.registerSuperUser(vendorRegistrationDTO);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String datetime = LocalDateTime.now().format(formatter);

        ApiResponse<Object> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Vendor registered successfully",
                datetime,
                responseMap
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register/supervisor")
    public ResponseEntity<ApiResponse<Object>> registerSuperUser(
            @RequestBody @Valid SupervisorRegistrationDTO supervisorRegistrationDTO,
            @RequestHeader("businessDetailsId") @NotNull Long businessDetailsId,
            @RequestHeader("superUserEid") @NotBlank String superUserEid) throws IllegalAccessException {

        if (!vendorUserService.isAllowed(superUserEid, "SuperUser"))
            throw new IllegalAccessException("User not authorized");

        HashMap<String, Long> responseMap = vendorUserService.registerSuperVisor(supervisorRegistrationDTO, businessDetailsId);

        ApiResponse<Object> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "SuperUser registered successfully",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")),
                responseMap
        );
        return ResponseEntity.ok(response);
    }
}