package openg2p.vendor.document.repository;

import openg2p.vendor.document.entity.DocumentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<DocumentDetails, Long> {
    List<DocumentDetails> findByProgramIdAndBeneficiaryIdAndFormId(String programId, String beneficiaryId, String formId);

}
