package openg2p.vendor.vendor.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;

@Getter
@Setter
public class VendorRegistrationDTO {

    @NotBlank(message = "Required")
    @Size(min = 3, max = 200, message = "Enter between 3 to 200 characters. Spaces, periods, commas & hyphens allowed.")
    @Pattern(regexp = "^[a-zA-Z0-9 .,\\-]*$", message = "Invalid characters. Only spaces, periods, commas & hyphens are allowed.")
    private String businessName;

    @Size(max = 200, message = "Enter address between 1 to 200 characters")
    @Pattern(regexp = "^[a-zA-Z0-9 .,\\-/]*$", message = "Invalid characters. Only spaces, commas, periods, hyphens, and forward slashes are allowed.")
    private String addressLine1;

    @Size(max = 200, message = "Enter address between 1 to 200 characters")
    @Pattern(regexp = "^[a-zA-Z0-9 .,\\-/]*$", message = "Invalid characters. Only spaces, commas, periods, hyphens, and forward slashes are allowed.")
    private String addressLine2;

    @Size(max = 200, message = "Enter address between 1 to 200 characters")
    @Pattern(regexp = "^[a-zA-Z0-9 .,\\-/]*$", message = "Invalid characters. Only spaces, commas, periods, hyphens, and forward slashes are allowed.")
    private String addressLine3;

    // Assuming you have a predefined list of towns to validate against
    // e.g., using @ValidTown to check against a list of allowed towns
    private String town;

    @Size(min = 6, max = 6, message = "Invalid postcode")
    @Pattern(regexp = "^(?!000000)[0-9]{6}$", message = "Invalid postcode")
    private String postcode;

    @Size(min = 3, max = 20, message = "Enter name between 3 to 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "No special characters allowed")
    private String businessType;

    @NotBlank(message = "Required")
    @Email(message = "Invalid email address")
    private String businessEmail;

    @Pattern(regexp = "^(?!0000000)[0-9]{7}$", message = "Invalid phone number")
    private String businessPhoneNumber;

    @NotNull(message = "VAT proof is Required")
    private MultipartFile vatProof;

    @NotNull(message = "Tax Proof is Required")
    private MultipartFile taxProof;

    @NotNull(message = "Signature is Required")
    private MultipartFile signature;

    // Assuming bank info is just a numeric input
    @Pattern(regexp = "^[0-9]*$", message = "Only numbers allowed")
    private String bankInfo;

    @NotNull(message = "EID is Required")
    private String eid;

    @NotNull(message = "Usertype is Required")
    private String userType;
}
