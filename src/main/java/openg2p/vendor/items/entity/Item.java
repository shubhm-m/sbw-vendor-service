package openg2p.vendor.items.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "items")
@Getter
@Setter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String category;
    private String description;
    private String modelName;
    private double warranty;
    private String condition;
    private double vendorAmount;
    private double maxAmount;
    private String serialId;
    private Long vendorId;

    @JsonIgnore
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @JsonIgnore
    private LocalDateTime updatedDate;

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

