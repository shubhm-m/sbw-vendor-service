package openg2p.vendor.vendor.repository;

import openg2p.vendor.vendor.entity.VendorBusinessDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorBusinessDetailsRepository extends JpaRepository<VendorBusinessDetails, Long> {
}
