package playground.apps.common;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import playground.model.entity.plain.User;
import playground.utils.UsetUtil;

import static playground.utils.ServletUtil.getClientIp;

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
    public String gotoPage(
            @PathVariable("menuNo") Long menuNo,
            HttpServletRequest request
            ) {

        User userDto = UsetUtil.getUser();
        svc.savePageAcsLog(userDto.getUserId(), menuNo, getClientIp(request));
        return svc.goToPage(menuNo);
    }
}
