package playground.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PageAcsLogDTO {
    private Long logNo;
    private Long userId;
    private Long menuId;
    private LocalDateTime acsDttm;

    @Builder
    public PageAcsLogDTO(Long logNo, Long userId, Long menuId, LocalDateTime acsDttm) {
        this.logNo = logNo;
        this.userId = userId;
        this.menuId = menuId;
        this.acsDttm = acsDttm;
    }
}
