package openg2p.vendor.items.repository;

import openg2p.vendor.items.entity.ItemTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemTransactionRepository extends JpaRepository<ItemTransactionEntity, Long> {
    Optional<ItemTransactionEntity> findFirstByVendorIdAndBeneficiaryIdAndItemCodeOrderByUpdatedDateDesc(
            Long vendorId,
            String beneficiaryId,
            String itemCode
    );
}
