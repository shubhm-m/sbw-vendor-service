package openg2p.vendor.document.service;

import openg2p.vendor.document.dto.DocumentUploadDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface DocumentService {
    public Map<String, Long> uploadDocument(DocumentUploadDto dto, MultipartFile document);
}
