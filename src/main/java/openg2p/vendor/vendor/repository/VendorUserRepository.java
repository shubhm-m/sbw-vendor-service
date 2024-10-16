package openg2p.vendor.vendor.repository;

import openg2p.vendor.vendor.entity.VendorUser;
import openg2p.vendor.vendor.service.SupervisorRegistrationProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VendorUserRepository extends JpaRepository<VendorUser, Long> {
    public Optional<VendorUser> findByEid(String eid);

    @Query(value = "SELECT ud.*, vu.eid, vu.user_type\n" +
            "FROM public.user_details ud\n" +
            "JOIN public.vendor_user vu ON ud.id = vu.user_details_id\n" +
            "WHERE vu.vendor_business_details_id =:vendorId ;\n", nativeQuery = true)
    public List<SupervisorRegistrationProjection> getUserListByVendorId(Long vendorId);
}
