package playground.apps.logging;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "PageAccessLog", description = "화면 진입 로그 조회 API")
@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/logging/page-access")
public class PageAcsLogCtl {
    private final PageAcsLogSvc svc;

    @GetMapping
    public ResponseEntity<?> getAllPageAcsLog() {

        return new ResponseEntity<>(svc.getAllPageAcsLog(), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchPageAcsLog(
            @RequestParam(required = false) String gubun,
            @RequestParam(required = false) String searchWord
    ) {
        Map<String, Object> map = new HashMap<>();
        map.put("gubun", gubun);
        map.put("searchWord", searchWord);

        return new ResponseEntity<>(svc.searchPageAcsLog(map), HttpStatus.OK);
    }
}
