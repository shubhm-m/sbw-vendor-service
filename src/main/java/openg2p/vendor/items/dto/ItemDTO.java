package openg2p.vendor.items.dto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Getter
@Setter
public class ItemDTO {

    private Long id;
    @NotBlank(message = "Required")
    private String category;
    private String description;
    @NotBlank(message = "Required")
    private String serialId;
    @NotBlank(message = "Required")
    private String modelName;
    @NotNull(message = "Required")
    private Double warranty;
    private String condition;
    @NotNull(message = "Required")
    private Double vendorAmount;
    @NotNull(message = "Required")
    private Long vendorId;
    private Double maxAmount;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
