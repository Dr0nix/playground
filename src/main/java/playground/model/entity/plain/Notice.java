package playground.model.entity.plain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "pg_notice", schema = "common")
@Getter
@NoArgsConstructor
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ntc_seq_gen")
    @SequenceGenerator(
            name = "ntc_seq_gen",
            sequenceName = "common.pg_notice_ntc_id_seq",
            allocationSize = 1
    )
    private Long ntcId;

    @Column(nullable = false, name = "title")
    private String title;

    @Column(name = "cont", columnDefinition = "TEXT")
    private String content;

    @Column(name = "pin_yn")
    private boolean pinned = false;

    @Column(name = "use_yn")
    private boolean visible = true;

    @Column(name = "view_cnt")
    private Long viewCount = 0L;

    @Column(nullable = false, name = "reg_user_nm")
    private String regUserNm;

    @Column(nullable = false, name = "reg_user_id")
    private Long regUserId;

    @Column(name = "reg_dttm")
    private LocalDateTime regDttm = LocalDateTime.now();

    @Column(name = "mod_dttm")
    private LocalDateTime modDttm = LocalDateTime.now();

    @Builder
    public Notice(String title, String content, boolean pinned, String regUserNm, Long regUserId) {
        this.title = title;
        this.content = content;
        this.pinned = pinned;
        this.regUserNm = regUserNm;
        this.regUserId = regUserId;
    }

    public void update(String title, String content, boolean pinned) {
        this.title = title;
        this.content = content;
        this.pinned = pinned;
    }

    public void softDelete() {
        this.visible = false;
    }

    public void incrementViewCount() {
        this.viewCount = (this.viewCount == null ? 0L : this.viewCount) + 1;
    }

    @PrePersist
    public void onCreate() {
        this.regDttm = LocalDateTime.now();
        this.modDttm = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.modDttm = LocalDateTime.now();
    }
}
