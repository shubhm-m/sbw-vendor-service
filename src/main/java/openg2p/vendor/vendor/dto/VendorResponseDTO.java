package openg2p.vendor.vendor.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
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
    private String firstName;
    private String lastName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String supervisorEmail;
    private String eid;
    private String userType;
}
