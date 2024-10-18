package openg2p.vendor.document.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "redemption_files")
public class RedemptionFilesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String filePath;
    private String fileType;
    @Column(updatable = false)
    @JsonIgnore
    private LocalDateTime createdDate = LocalDateTime.now();
    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
    }
}
