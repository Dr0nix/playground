package playground.model.dto;

import lombok.Data;

@Data
public class PageAcsLogRequestDTO {
    private Long userId;
    private Long menuNo;
    private String userIp;

    public PageAcsLogRequestDTO(Long userId, Long menuNo, String userIp) {
        this.userId = userId;
        this.menuNo = menuNo;
        this.userIp = userIp;
    }
}
