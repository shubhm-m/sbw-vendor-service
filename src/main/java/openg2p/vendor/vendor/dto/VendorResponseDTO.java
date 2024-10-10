package openg2p.vendor.vendor.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class VendorResponseDTO {
    private Long id;
    private String businessName;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String town;
    private String postcode;
    private String businessType;
    private String businessEmail;
    private String businessPhoneNumber;
    private String eid;
    private String userType;
}
