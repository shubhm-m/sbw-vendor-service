package openg2p.vendor.items.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "item_transaction")
@Entity
@Getter
@Setter
public class ItemTransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long vendorId;
    private String beneficiaryId;
    private String itemCode;
    private Long vendorItemId;
    private Long vendorImageId;
    private Long invoiceId;
    private Long beneficiaryImageId;
    private String status;
    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @PrePersist
    protected void onCreate() {
        updatedDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedDate = LocalDateTime.now();
    }
}
