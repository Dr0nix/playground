package playground.model.entity.plain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import playground.enums.MenuType;
import playground.model.entity.generic.AuditableEntity;

@Entity
@Table(name = "pg_menu", schema = "common")
@Getter
@RequiredArgsConstructor
public class Menu extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuNo;

    @Column(nullable = false)
    private String menuNm;

    private String menuDesc;

    @Column(nullable = false)
    private int menuLv;

    @Column(nullable = false)
    private Long prntNo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MenuType menuTp;

    private String menuUrl;

    private String menuParam;

    private Boolean useYn;

    @Builder
    public Menu(Long menuNo, String menuNm, String menuDesc, int menuLv, Long prntNo, MenuType menuTp, String menuUrl, String menuParam, Boolean useYn) {
        this.menuNo = menuNo;
        this.menuNm = menuNm;
        this.menuDesc = menuDesc;
        this.menuLv = menuLv;
        this.prntNo = prntNo;
        this.menuTp = menuTp;
        this.menuUrl = menuUrl;
        this.menuParam = menuParam;
        this.useYn = useYn;
    }
}
