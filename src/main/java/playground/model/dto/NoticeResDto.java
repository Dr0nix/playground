package playground.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class NoticeResDto {
    private Long ntcId;
    private String title;
    private boolean pinYn;
    private Long viewCount;
    private String regUserNm;
    private LocalDateTime regDttm;
}
