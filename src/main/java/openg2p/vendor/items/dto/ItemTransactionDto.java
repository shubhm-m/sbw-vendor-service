package openg2p.vendor.items.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class ItemTransactionDto {
    private Long id;
    @NotNull(message = "Required")
    private Long vendorId;
    @NotBlank(message = "Required")
    private String beneficiaryId;
    @NotBlank(message = "Required")
    private String itemCode;
    @NotNull(message = "Required")
    private Long vendorItemId;
    private Long vendorImageId;
    private Long beneficiaryImageId;
    private String status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:MM:SS")
    private LocalDateTime createdDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:MM:SS")
    private LocalDateTime updatedDate;
}
