package openg2p.vendor.vendor.repository;

import openg2p.vendor.document.entity.RedemptionFilesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedemptionFileRepository extends JpaRepository<RedemptionFilesEntity, Long> {
}
