package openg2p.vendor.items;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ItemDTO {

    private Long id;
    private String category;
    private String description;
    private String modelName;
    private double warranty;
    private String condition;
    private double vendorAmount;
    private double maxAmount;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
