package openg2p.vendor.document.service;

import openg2p.vendor.document.dto.DocumentUploadDto;
import openg2p.vendor.document.entity.RedemptionFilesEntity;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface DocumentService {
    Map<String, Long> uploadDocument(DocumentUploadDto dto, MultipartFile document);
    RedemptionFilesEntity uploadRedemptionDocument(MultipartFile file, String fileType);
    Resource viewRedemptionFile(Long id);
    List<DocumentUploadDto> redemptionDocumentList(String beneficiaryId, String programId, String formId);
    Resource viewFormDocument(Long id);
}
