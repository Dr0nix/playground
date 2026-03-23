package playground.model.entity.plain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "pg_notice", schema = "common")
@Getter
@Setter
@RequiredArgsConstructor
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

    @Column(name = "cont")
    private String content;

    @Column(name = "pin_yn")
    private boolean isPinned = false;

    @Column(name = "use_yn")
    private boolean isVisible = true;

    @Column(name = "view_cnt")
    private Long viewCount = 0L;

    @Column(nullable = false, name = "reg_user_nm")
    private String regUserNm;

    @Column(nullable = false, name = "reg_user_id")
    private Long regUserId;

    @Column(name = "reg_dttm")
    private LocalDateTime regDttm = LocalDateTime.now();

    @Column(name = "mod_dttm")
    private LocalDateTime modDttm =  LocalDateTime.now();
}
