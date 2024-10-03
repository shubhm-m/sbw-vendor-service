package openg2p.vendor.redemptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RedemptionServiceImpl implements RedemptionService {

    @Autowired
    private RedemptionRepository redemptionRepository;

    @Override
    public Redemption addRedemption(RedemptionDTO redemptionDTO) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Redemption redemption = new Redemption();
        redemption.setBeneficiaryId(redemptionDTO.getBeneficiaryId());
        redemption.setVendorId(redemptionDTO.getVendorId());
        redemption.setVoucherId(redemptionDTO.getVoucherId());
        redemption.setItems(objectMapper.writeValueAsString(redemptionDTO.getItems()));
        return redemptionRepository.save(redemption);
    }
}

