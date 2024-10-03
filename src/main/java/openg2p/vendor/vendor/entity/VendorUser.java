package openg2p.vendor.vendor.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

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
}

