package openg2p.vendor.redemptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RedemptionDTO {
    private Long voucherId;
    private Long beneficiaryId;
    private Long vendorId;
    private Object items;
}