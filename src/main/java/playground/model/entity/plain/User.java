package playground.model.entity.plain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import playground.model.entity.generic.AuditableEntity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "pg_user", schema = "common")
@Getter
@RequiredArgsConstructor
public class User extends AuditableEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_gen")
    @SequenceGenerator(
            name = "user_seq_gen",
            sequenceName = "common.pg_user_user_id_seq",
            allocationSize = 1
    )
    private Long userId;

    @Column(nullable = false)
    private String userNm;

    @Column
    private String userNickname;

    @Email
    @Column(nullable = false, unique = true)
    private String userEmail;

    @Column(nullable = false)
    private String userPw;

    private Boolean useYn;

    @Builder
    public User(Long userId, String userNm, String userNickname, String userEmail, String userPw, Boolean useYn, LocalDateTime createdAt, LocalDateTime modifiedAt, String createdBy, String modifiedBy) {
        this.userId = userId;
        this.userNm = userNm;
        this.userNickname = userNickname;
        this.userEmail = userEmail;
        this.userPw = userPw;
        this.useYn = useYn;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.modifiedAt = modifiedAt;
        this.modifiedBy = modifiedBy;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return userPw;
    }

    @Override
    public String getUsername() {
        return userNm;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
