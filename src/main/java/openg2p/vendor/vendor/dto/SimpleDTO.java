package openg2p.vendor.vendor.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SimpleDTO {
    @NotBlank(message = "Field is required")
    private String requiredField;

    // Getter and setter
}

