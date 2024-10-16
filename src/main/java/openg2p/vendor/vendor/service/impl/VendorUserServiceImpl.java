package openg2p.vendor.vendor.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import openg2p.vendor.vendor.dto.SupervisorRegistrationDTO;
import openg2p.vendor.vendor.dto.VendorRegistrationDTO;
import openg2p.vendor.vendor.dto.VendorResponseDTO;
import openg2p.vendor.vendor.entity.UserDetails;
import openg2p.vendor.vendor.entity.VendorBusinessDetails;
import openg2p.vendor.vendor.entity.VendorUser;
import openg2p.vendor.vendor.repository.UserDetailsRepository;
import openg2p.vendor.vendor.repository.VendorBusinessDetailsRepository;
import openg2p.vendor.vendor.repository.VendorUserRepository;
import openg2p.vendor.vendor.service.SupervisorRegistrationProjection;
import openg2p.vendor.vendor.service.VendorUserService;
import openg2p.vendor.util.VendorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ValidationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class VendorUserServiceImpl implements VendorUserService {

    @Autowired
    private VendorBusinessDetailsRepository vendorBusinessDetailsRepository;

    @Autowired
    private VendorUserRepository vendorUserRepository;

    @Autowired
    private VendorMapper vendorMapper;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Value("${BASE_DIRECTORY}")
    private String baseDirectory;

    @Override
    public boolean isAllowed(String eId, String userType) {
        Optional<VendorUser> vendorUserOptional = vendorUserRepository.findByEid(eId);
        return Optional.ofNullable(vendorUserOptional)
                .flatMap(vendorUserOpt -> vendorUserOpt.map(vendorUser -> vendorUser.getUserType().equals(userType)))
                .orElse(false);
    }

    @Override
    public HashMap<String, Long> registerSuperUser(VendorRegistrationDTO registrationDTO) {
        Optional<VendorUser> vendorUserOptional = vendorUserRepository.findByEid(registrationDTO.getEid());
        if (vendorUserOptional.isPresent())
            throw new ValidationException("User already exist with E-ID: "+ registrationDTO.getEid());

        VendorBusinessDetails vendorBusinessDetails = vendorMapper.toVendorBusinessDetails(registrationDTO);
        // Extract business name for folder creation
        String businessName = registrationDTO.getBusinessName().replaceAll("\\s+", "_"); // Replace spaces with underscores for directory naming

        // Save uploaded files and get paths
        String vatProofPath = saveFile(registrationDTO.getVatProof(), businessName);
        String taxProofPath = saveFile(registrationDTO.getTaxProof(), businessName);
        String signaturePath = saveFile(registrationDTO.getSignature(), businessName);

        // Map DTO to VendorBusinessDetails
        vendorBusinessDetails.setVatProofPath(vatProofPath);
        vendorBusinessDetails.setTaxProofPath(taxProofPath);
        vendorBusinessDetails.setSignaturePath(signaturePath);

        // Save the vendor details
        vendorBusinessDetails = vendorBusinessDetailsRepository.save(vendorBusinessDetails);

        // Map DTO to VendorUser, passing the saved vendor ID
        VendorUser vendorUser = vendorMapper.toVendorUser(registrationDTO, vendorBusinessDetails.getId());

        // Save the user details
        vendorUser = vendorUserRepository.save(vendorUser);
        HashMap<String, Long> responseMap = new HashMap<>();
        responseMap.put("User ID",vendorUser.getId());
        responseMap.put("Business ID", vendorBusinessDetails.getId());
        return responseMap;
    }

    @Override
    public HashMap<String, Long> registerSuperVisor(SupervisorRegistrationDTO supervisorRegistrationDTO, Long businessDetailsId) {
        Optional<VendorUser> vendorUserOptional = vendorUserRepository.findByEid(supervisorRegistrationDTO.getEid());
        if (vendorUserOptional.isPresent())
            throw new ValidationException("User already exist with E-ID: "+ supervisorRegistrationDTO.getEid());

        UserDetails userDetails = vendorMapper.toUserDetails(supervisorRegistrationDTO);
        userDetails = userDetailsRepository.save(userDetails);

        VendorUser vendorUser = vendorMapper.toVendorUser(supervisorRegistrationDTO, userDetails.getId());
        vendorUser.setVendorBusinessDetailsId(businessDetailsId);
        vendorUser = vendorUserRepository.save(vendorUser);

        HashMap<String, Long> responseMap = new HashMap<>();
        responseMap.put("User ID", vendorUser.getId());
        responseMap.put("User Details ID", userDetails.getId());
        return responseMap;
    }

    @Override
    public VendorResponseDTO getVendorByEid(String eid) {
        Optional<VendorUser> vendorUserOptional = vendorUserRepository.findByEid(eid);
        if (vendorUserOptional.isEmpty())
            throw new ValidationException("Vendor Not Found");
        VendorResponseDTO vendorResponseDTO = new VendorResponseDTO();
        if (vendorUserOptional.get().getUserType().equals("SuperUser")) {
            Optional<VendorBusinessDetails> vendorBusinessDetails =
                    vendorBusinessDetailsRepository.findById(vendorUserOptional.get().getVendorBusinessDetailsId());
            vendorResponseDTO = new ObjectMapper().convertValue(vendorBusinessDetails.get(), VendorResponseDTO.class);
        }
        else {
            Optional<UserDetails> userDetailsOptional = userDetailsRepository.findById(
                    vendorUserOptional.get().getUserDetailsId()
            );
            vendorResponseDTO = vendorMapper.toVendorResponseDTO(userDetailsOptional.get());
//            vendorResponseDTO.setFirstName(userDetailsOptional.get().getFirstName());
//            vendorResponseDTO.setLastName(userDetailsOptional.get().getLastName());
//            vendorResponseDTO.setSupervisorEmail(userDetailsOptional.get().getSupervisorEmail());
//            vendorResponseDTO.setPhoneNumber(userDetailsOptional.get().getPhoneNumber());
        }
        vendorResponseDTO.setId(vendorUserOptional.get().getId());
        vendorResponseDTO.setEid(vendorUserOptional.get().getEid());
        vendorResponseDTO.setUserType(vendorUserOptional.get().getUserType());
        return vendorResponseDTO;
    }

    @Override
    public List<SupervisorRegistrationProjection> getUserListByVendorId(Long vendorId) {
        return vendorUserRepository.getUserListByVendorId(vendorId);
    }

    private String saveFile(MultipartFile file, String businessName) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        String directoryPath = baseDirectory + File.separator + businessName;

        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String originalFilename = file.getOriginalFilename();
        String newFilename = System.currentTimeMillis() + "_" + originalFilename;
        String filePath = directoryPath + File.separator + newFilename;

        try {
            file.transferTo(new File(filePath));
            return filePath;
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException("Could not save file: " + e.getMessage(), e);
        }
    }
}
