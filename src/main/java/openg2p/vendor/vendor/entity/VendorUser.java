package openg2p.vendor.vendor.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vendor_user")
@Setter
@Getter
public class VendorUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String eid;

    @Column(nullable = false)
    private String userType;

    @JsonIgnore
    @Column(name = "vendor_business_details_id")
    private Long vendorBusinessDetailsId;

    @JsonIgnore
    @Column(name = "user_details_id")
    private Long userDetailsId;

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

