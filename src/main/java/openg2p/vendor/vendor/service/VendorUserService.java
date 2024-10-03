package openg2p.vendor.vendor.service;

import openg2p.vendor.vendor.dto.VendorRegistrationDTO;

import java.util.HashMap;

public interface VendorUserService {
    public HashMap<String, Long> registerSuperUser(VendorRegistrationDTO userDTO);
}