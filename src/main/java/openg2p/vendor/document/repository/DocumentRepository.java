package openg2p.vendor.document.repository;

import openg2p.vendor.document.entity.DocumentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<DocumentDetails, Long> {
}
