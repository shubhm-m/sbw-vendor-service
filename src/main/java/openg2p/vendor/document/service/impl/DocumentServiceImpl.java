package openg2p.vendor.document.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import openg2p.vendor.document.dto.DocumentUploadDto;
import openg2p.vendor.document.entity.DocumentDetails;
import openg2p.vendor.document.entity.RedemptionFilesEntity;
import openg2p.vendor.document.repository.DocumentRepository;
import openg2p.vendor.document.service.DocumentService;
import openg2p.vendor.vendor.repository.RedemptionFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ValidationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DocumentServiceImpl implements DocumentService {

    @Value("${DOCUMENT_BASE_DIRECTORY}")
    private String baseDirectory;

    @Value("${REDEMPTION_FILE_BASE_DIRECTORY}")
    private String redemptionFileBaseDirectory;

    @Autowired
    RedemptionFileRepository redemptionFileRepository;

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



    @Override
    public RedemptionFilesEntity uploadRedemptionDocument(MultipartFile file, String fileType) {
        if (file == null || file.isEmpty()) {
            throw new ValidationException("Document is empty");
        }
        String directoryPath = redemptionFileBaseDirectory+ File.separator + fileType;

        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename != null && originalFilename.contains(".")
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : "";
        String newFilename = UUID.randomUUID().toString() + fileExtension;

        String filePath = directoryPath + File.separator + newFilename;

        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException("Could not save file: " + e.getMessage(), e);
        }

        RedemptionFilesEntity redemptionFilesEntity = new RedemptionFilesEntity();
        redemptionFilesEntity.setFilePath(filePath);
        redemptionFilesEntity.setFileType(fileType);

        return redemptionFileRepository.save(redemptionFilesEntity);
    }

    @Override
    public Resource viewRedemptionFile(Long id) {
        Optional<RedemptionFilesEntity> redemptionFilesEntity = redemptionFileRepository.findById(id);
        if (redemptionFilesEntity.isEmpty()) {
            throw new ValidationException("Invalid File ID");
        }
        String filePath = redemptionFilesEntity.get().getFilePath();
        return getFile(filePath);
    }

    @Override
    public List<DocumentUploadDto> redemptionDocumentList(String beneficiaryId, String programId, String formId) {
        List<DocumentDetails> documentDetailsList = documentRepository.findByProgramIdAndBeneficiaryIdAndFormId(programId, beneficiaryId, formId);
        if (documentDetailsList == null || documentDetailsList.isEmpty())
            return null;
        ObjectMapper objectMapper = new ObjectMapper();
        return documentDetailsList.stream()
                .map(documentDetails -> objectMapper.convertValue(documentDetails, DocumentUploadDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Resource viewFormDocument(Long id) {
        Optional<DocumentDetails> documentDetails = documentRepository.findById(id);
        if (documentDetails.isEmpty()) {
            throw new ValidationException("Invalid Document ID");
        }
        String filePath = documentDetails.get().getDocumentLocation();
        return getFile(filePath);
    }

    private Resource getFile(String filePath){
        try {
            Path path = Paths.get(filePath).normalize();
            Resource resource = new UrlResource(path.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new ValidationException("File not found");
            }
        } catch (Exception e) {
            throw new ValidationException("Could not retrieve file", e);
        }
    }
}
