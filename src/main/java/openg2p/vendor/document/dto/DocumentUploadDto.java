package openg2p.vendor.document.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class DocumentUploadDto {
    private String programId;
    private String beneficiaryId;
    private String formId;
    private String documentName;
}