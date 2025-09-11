package playground.enums;

import lombok.Getter;

@Getter
public enum MenuType {
    GR("그룹메뉴"),
    P("페이지메뉴")

    ;

    private String typeName;

    MenuType(String typeName) {
        this.typeName = typeName;
    }
}
