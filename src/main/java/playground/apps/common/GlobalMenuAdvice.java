package playground.apps.common;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import playground.model.dto.MenuNodeDTO;

import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalMenuAdvice {

    private final MenuSvc menuSvc;

    // 모든 뷰 모델에 자동 포함됨: ${menuTree}
    @ModelAttribute("menuTree")
    public List<MenuNodeDTO> menuTree() {
        return menuSvc.getMenuTree();
    }
}
