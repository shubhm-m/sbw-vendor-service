package openg2p.vendor.document.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import openg2p.vendor.document.dto.DocumentUploadDto;
import openg2p.vendor.document.service.DocumentService;
import openg2p.vendor.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

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
}

