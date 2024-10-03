package openg2p.vendor.util;

import openg2p.vendor.vendor.dto.VendorRegistrationDTO;
import openg2p.vendor.vendor.entity.VendorBusinessDetails;
import openg2p.vendor.vendor.entity.VendorUser;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class VendorMapper {

    @Autowired
    private Validator validator;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "vatProofPath", ignore = true) // Ignore the path for mapping
    @Mapping(target = "taxProofPath", ignore = true) // Ignore the path for mapping
    @Mapping(target = "signaturePath", ignore = true) // Ignore the path for mapping
    public abstract VendorBusinessDetails toVendorBusinessDetails(VendorRegistrationDTO dto);

    @BeforeMapping
    protected void validate(VendorRegistrationDTO dto) {
        Set<ConstraintViolation<VendorRegistrationDTO>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "vendorBusinessDetailsId", source = "vendorBusinessDetailsId")
    public abstract VendorUser toVendorUser(VendorRegistrationDTO dto, Long vendorBusinessDetailsId);
}
