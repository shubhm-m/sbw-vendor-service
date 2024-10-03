package openg2p.vendor.vendor.repository;
import openg2p.vendor.vendor.entity.VendorUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorUserRepository extends JpaRepository<VendorUser, Long> {
}
