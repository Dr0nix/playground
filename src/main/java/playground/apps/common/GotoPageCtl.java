package playground.apps.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class GotoPageCtl {
    private final GotoPageSvc svc;

    @GetMapping("/")
    public String redirectToHome() {
        return svc.redirectToHome();
    }

    @GetMapping("/home")
    public String goToHome() {
        String url = svc.goToPage(6L);

        return url;
    }
}
