package openg2p.vendor.vendor.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vendor_business_details")
@Getter
@Setter
public class VendorBusinessDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String businessName;

    @Column
    private String addressLine1;

    @Column
    private String addressLine2;

    @Column
    private String addressLine3;

    @Column
    private String town;

    @Column
    private String postcode;

    @Column
    private String businessType;

    @Column(nullable = false)
    private String businessEmail;

    @Column
    private String businessPhoneNumber;

    @Column(nullable = false)
    private String vatProofPath;

    @Column(nullable = false)
    private String taxProofPath;

    @Column
    private String bankInfo;

    @Column(nullable = false)
    private String signaturePath;

    @JsonIgnore
    @Column(updatable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @JsonIgnore
    private LocalDateTime updatedDate = LocalDateTime.now();

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
        updatedDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedDate = LocalDateTime.now();
    }
}
