package playground.apps.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import playground.enums.MenuType;
import playground.model.entity.plain.Menu;
import playground.model.repository.MenuRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class GotoPageSvc {
    private final MenuRepository menuRepo;

    public String redirectToHome() {
        log.info("redirectToHome");

        return "redirect:/home";
    }

    public String goToPage(Long menuNo) {
        Menu menu = menuRepo.findByMenuNo(menuNo)
                .orElseThrow(() -> new IllegalArgumentException("menu not found: " + menuNo));

        if(!menu.getUseYn() || !menu.getMenuTp().equals(MenuType.P)) {
            throw new IllegalArgumentException("menu disabled : (" + menuNo + ", " + menu.getMenuNm() + ")");
        }

        String url = (menu.getMenuUrl() == null) ? "" : menu.getMenuUrl().trim();
        if(url.isEmpty()) {
            throw new IllegalArgumentException("menu url is empty : " + menuNo + ", " + menu.getMenuNm() + ")");
        }

        String param = (menu.getMenuParam() == null || menu.getMenuParam().isBlank())
                ? "" : "?" + menu.getMenuParam().trim();

        String pageUrl = url + param;

        return pageUrl;
    }
}
