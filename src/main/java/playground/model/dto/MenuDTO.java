package playground.model.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import playground.enums.MenuType;

import java.time.LocalDateTime;

public class MenuDTO {
    private Long menuNo;
    private String menuNm;
    private String menuDesc;
    private int menuLv;
    private Long prntNo;
    private MenuType menuTp;
    private String menuUrl;
    private String menuParam;
    private Boolean useYn;
    private String createdBy;
    private LocalDateTime createdAt;
    private String modifiedBy;
    private LocalDateTime modifiedAt;

    @Builder
    public MenuDTO(Long menuNo, String menuNm, String menuDesc, int menuLv, Long prntNo,
                   MenuType menuTp, String menuUrl, String menuParam, Boolean useYn,
                   String createdBy, LocalDateTime createdAt, String modifiedBy, LocalDateTime modifiedAt) {
        this.menuNo = menuNo;
        this.menuNm = menuNm;
        this.menuDesc = menuDesc;
        this.menuLv = menuLv;
        this.prntNo = prntNo;
        this.menuTp = menuTp;
        this.menuUrl = menuUrl;
        this.menuParam = menuParam;
        this.useYn = useYn;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.modifiedBy = modifiedBy;
        this.modifiedAt = modifiedAt;
    }
}
