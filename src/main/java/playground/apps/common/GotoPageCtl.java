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

    @GetMapping("/login-page")
    public String redirectToLogin() {
        return svc.redirectToLogin();
    }

    @GetMapping("/signup-page")
    public String gotoSignup() {
        return svc.gotoSignup();
    }

    @GetMapping("/gotopage/{menuNo}")
    public String gotoPage(@PathVariable("menuNo") Long menuNo) {
        return svc.goToPage(menuNo);
    }
}
