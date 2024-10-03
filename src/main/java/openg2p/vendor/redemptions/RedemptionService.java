package openg2p.vendor.redemptions;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface RedemptionService {
    Redemption addRedemption(RedemptionDTO redemptionDTO) throws JsonProcessingException;
}