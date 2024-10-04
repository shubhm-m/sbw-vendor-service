package openg2p.vendor.vendor.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
public class SupervisorRegistrationDTO {

    @NotBlank(message = "Role is required")
    private String userType;

    @NotBlank(message = "First Name is required")
    @Pattern(regexp = "^[a-zA-Z. ]{3,20}$", message = "Enter a valid first name between 3 to 20 characters")
    private String firstName;

    @NotBlank(message = "Last Name is required")
    @Pattern(regexp = "^[a-zA-Z. ]{3,20}$", message = "Enter a valid last name between 3 to 20 characters")
    private String lastName;

    @NotNull(message = "Date of Birth is required")
    @Past(message = "Please select a valid date")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Phone Number is required")
    @Pattern(regexp = "^[0-9]{7}$", message = "Phone number must be exactly 7 digits")
    private String phoneNumber;

    @Email(message = "Enter a valid email address")
    private String supervisorEmail;

    @NotBlank(message = "e-ID is required")
    @Pattern(regexp = "^[0-9]{9}$", message = "Enter a valid e-ID")
    private String eid;
}
