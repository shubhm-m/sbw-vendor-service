package openg2p.vendor.vendor.service.impl;

import lombok.extern.slf4j.Slf4j;
import openg2p.vendor.vendor.dto.VendorRegistrationDTO;
import openg2p.vendor.vendor.entity.VendorBusinessDetails;
import openg2p.vendor.vendor.entity.VendorUser;
import openg2p.vendor.vendor.repository.VendorBusinessDetailsRepository;
import openg2p.vendor.vendor.repository.VendorUserRepository;
import openg2p.vendor.vendor.service.VendorUserService;
import openg2p.vendor.util.VendorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@Service
@Slf4j
public class VendorUserServiceImpl implements VendorUserService {

    @Autowired
    private VendorBusinessDetailsRepository vendorBusinessDetailsRepository;

    @Autowired
    private VendorUserRepository vendorUserRepository;

    @Autowired
    private VendorMapper vendorMapper;

    @Value("${BASE_DIRECTORY}")
    private String baseDirectory;

    @Override
    public HashMap<String, Long> registerSuperUser(VendorRegistrationDTO registrationDTO) {
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
