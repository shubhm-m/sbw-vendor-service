package openg2p.vendor.document.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import openg2p.vendor.document.dto.DocumentUploadDto;
import openg2p.vendor.document.entity.DocumentDetails;
import openg2p.vendor.document.repository.DocumentRepository;
import openg2p.vendor.document.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ValidationException;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Service
public class DocumentServiceImpl implements DocumentService {

    @Value("${DOCUMENT_BASE_DIRECTORY}")
    private String baseDirectory;

    @Autowired
    DocumentRepository documentRepository;

    @Override
    public Map<String, Long> uploadDocument(DocumentUploadDto dto, MultipartFile document) {
        if (document == null || document.isEmpty()) {
            throw new ValidationException("Document is empty");
        }

        String directoryPath = baseDirectory + File.separator + dto.getBeneficiaryId();

        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String originalFilename = document.getOriginalFilename();
        String newFilename = System.currentTimeMillis() + "_" + originalFilename;
        String filePath = directoryPath + File.separator + newFilename;

        try {
            document.transferTo(new File(filePath));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException("Could not save file: " + e.getMessage(), e);
        }

        DocumentDetails documentDetails = new ObjectMapper().convertValue(dto, DocumentDetails.class);
        documentDetails.setDocumentLocation(filePath);
        documentDetails = documentRepository.save(documentDetails);
        return Map.of("Document ID", documentDetails.getId());
    }
}
