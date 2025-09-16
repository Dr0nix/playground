package playground.model.entity.generic;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@Setter(AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public class AuditableEntity implements Serializable {
    @CreatedBy
    @Column(name = "created_by", updatable = false, nullable = false)
    public String createdBy;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    public LocalDateTime createdAt = LocalDateTime.now();

    @LastModifiedBy
    @Column(name = "modified_by", nullable = false)
    public String modifiedBy;

    @LastModifiedDate
    @Column(name = "modified_at", nullable = false)
    public LocalDateTime modifiedAt = LocalDateTime.now();
}
