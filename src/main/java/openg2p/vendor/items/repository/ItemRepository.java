package openg2p.vendor.items.repository;
import openg2p.vendor.items.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findBySerialId(String serialId);
    List<Item> findByVendorId(Long vendorId);
    Optional<Item> findBySerialIdAndIdNot(String serialId, Long id);
}

