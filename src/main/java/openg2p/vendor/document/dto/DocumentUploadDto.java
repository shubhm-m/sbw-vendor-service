package openg2p.vendor.document.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentUploadDto {
    private Long id;
    private String programId;
    private String beneficiaryId;
    private String formId;
    private String documentName;
}