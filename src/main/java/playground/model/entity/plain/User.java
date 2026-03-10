package playground.model.entity.plain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import playground.model.entity.generic.AuditableEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "pg_user", schema = "common")
@Getter
@RequiredArgsConstructor
public class User extends AuditableEntity {

    @Id
    private Long userId;

    @Column(nullable = false)
    private String userNm;

    @Email
    @Column(nullable = false, unique = true)
    private String userEmail;

    @Column(nullable = false)
    private String userPw;

    private Boolean useYn;

    @Builder
    public User(Long userId, String userNm, String userEmail, String userPw, Boolean useYn, LocalDateTime createdAt, LocalDateTime modifiedAt, String createdBy, String modifiedBy) {
        this.userId = userId;
        this.userNm = userNm;
        this.userEmail = userEmail;
        this.userPw = userPw;
        this.useYn = useYn;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.modifiedAt = modifiedAt;
        this.modifiedBy = modifiedBy;
    }
}
