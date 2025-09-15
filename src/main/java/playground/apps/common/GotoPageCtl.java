package playground.apps.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@Slf4j
@RequiredArgsConstructor
public class GotoPageCtl {
    private final GotoPageSvc svc;

    @GetMapping("/")
    public String redirectToHome() {
        return svc.redirectToHome();
    }

    @GetMapping("/loginOrigin")
    public String goToHome() {
        return "default-login";
    }

    @GetMapping("/gotopage/{menuNo}")
    public String gptoPage(@PathVariable Long menuNo) {
        return svc.goToPage(menuNo);
    }
}
