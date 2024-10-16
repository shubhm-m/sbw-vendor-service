package openg2p.vendor.vendor.service;

import openg2p.vendor.vendor.dto.SupervisorRegistrationDTO;
import openg2p.vendor.vendor.dto.VendorRegistrationDTO;
import openg2p.vendor.vendor.dto.VendorResponseDTO;

import java.util.HashMap;
import java.util.List;

public interface VendorUserService {
    public boolean isAllowed(String eId, String userType);
    public HashMap<String, Long> registerSuperUser(VendorRegistrationDTO userDTO);
    public HashMap<String, Long> registerSuperVisor(SupervisorRegistrationDTO supervisorRegistrationDTO, Long businessDetailsId);
    public VendorResponseDTO getVendorByEid(String eid);
    public List<SupervisorRegistrationProjection> getUserListByVendorId(Long vendorId);
}