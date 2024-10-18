package openg2p.vendor.document.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import openg2p.vendor.document.dto.DocumentUploadDto;
import openg2p.vendor.document.entity.RedemptionFilesEntity;
import openg2p.vendor.document.service.DocumentService;
import openg2p.vendor.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import org.springframework.core.io.Resource;


@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<Object>> uploadDocument(
            @RequestPart("documentDto") String documentDto,
            @RequestPart(value = "document", required = false) MultipartFile document) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        DocumentUploadDto documentUploadDto = objectMapper.readValue(documentDto, DocumentUploadDto.class);
        Map<String, Long> responseMap = documentService.uploadDocument(documentUploadDto, document);

        ApiResponse<Object> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Document uploaded successfully",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")),
                responseMap
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<Object>> uploadRedemptionFile(
            @RequestParam String beneficiaryId,
            @RequestParam String formId,
            @RequestParam String programId){
        List<DocumentUploadDto> documentUploadDtoList = documentService.redemptionDocumentList(beneficiaryId, programId, formId);
        ApiResponse<Object> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Document Details Fetched successfully",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")),
                documentUploadDtoList
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/redemption/upload")
    public ResponseEntity<ApiResponse<Object>> uploadRedemptionFile(
            @RequestPart("fileType") String fileType,
            @RequestPart(value = "document", required = false) MultipartFile file){
        RedemptionFilesEntity redemptionFilesEntity = documentService.uploadRedemptionDocument(file, fileType);
        ApiResponse<Object> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Document uploaded successfully",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")),
                redemptionFilesEntity
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/redemption/view/{id}")
    public ResponseEntity<Resource> viewFile(@PathVariable Long id) {
        Resource file = documentService.viewRedemptionFile(id);

        String contentType;
        try {
            contentType = Files.probeContentType(Paths.get(file.getFile().getAbsolutePath()));
        } catch (IOException ex) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE; // Fallback to binary stream
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }


    @GetMapping("/view/{id}")
    public ResponseEntity<Resource> viewFormDocument(@PathVariable Long id) {
        Resource file = documentService.viewFormDocument(id);

        String contentType;
        try {
            contentType = Files.probeContentType(Paths.get(file.getFile().getAbsolutePath()));
        } catch (IOException ex) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE; // Fallback to binary stream
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

}

