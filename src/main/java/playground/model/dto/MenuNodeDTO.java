package playground.model.dto;

import lombok.Builder;
import lombok.Data;
import playground.enums.MenuType;
import playground.model.entity.plain.Menu;

import java.util.ArrayList;
import java.util.List;

@Data
public class MenuNodeDTO {
    private Long id;
    private String name;
    private MenuType type;
    private String url;
    private String param;
    private List<MenuNodeDTO> children = new ArrayList<>();

    public boolean isGroup()  {
        return type == MenuType.GR;
    }

    public boolean isPage() {
        return type == MenuType.P;
    }

    public static MenuNodeDTO of(Menu m){
        MenuNodeDTO n = new MenuNodeDTO();
        n.id    = m.getMenuNo();
        n.name  = m.getMenuNm();
        n.type  = m.getMenuTp();
        n.url   = m.getMenuUrl();
        n.param = m.getMenuParam();
        return n;
    }
}
