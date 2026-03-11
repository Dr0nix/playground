package playground.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PageAcsLogResponseDTO {
    private Long logNo;
    private String menuNm;
    private Long userId;
    private String userNm;
    private LocalDateTime acsDttm;

    @Builder
    public PageAcsLogResponseDTO(Long logNo, String menuNm, Long userId, String userNm, LocalDateTime acsDttm) {
        this.logNo = logNo;
        this.menuNm = menuNm;
        this.userId = userId;
        this.userNm = userNm;
        this.acsDttm = acsDttm;
    }
}
