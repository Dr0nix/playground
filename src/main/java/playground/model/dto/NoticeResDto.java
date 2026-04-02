package playground.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class NoticeResDto {
    private Long ntcId;
    private String title;
    private String content;
    private boolean pinYn;
    private Long viewCount;
    private String regUserNm;
    private LocalDateTime regDttm;

    // 목록용 (content 제외)
    public NoticeResDto(Long ntcId, String title, boolean pinYn, Long viewCount, String regUserNm, LocalDateTime regDttm) {
        this(ntcId, title, null, pinYn, viewCount, regUserNm, regDttm);
    }
}
