package playground.enums;

import lombok.Getter;

@Getter
public enum MenuType {
    GR("GR", "그룹메뉴"),
    P("P", "페이지메뉴")

    ;

    private String typeCode;
    private String typeName;

    MenuType(String typeCode, String typeName) {
        this.typeCode = typeCode;
        this.typeName = typeName;
    }
}
