package playground.apps.community;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import playground.model.dto.NoticeResDto;

@Controller
@RequestMapping("/comm/ntc")
@Slf4j
@RequiredArgsConstructor
public class NoticeCtl {
    private final NoticeSvc svc;

    @GetMapping
    public ResponseEntity<?> getNoticeList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<NoticeResDto> result = svc.getNoticePage(page, size);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{ntcId}")
    public ResponseEntity<?> getSingleNotice(
            @PathVariable Long ntcId
    ) {
        return ResponseEntity.ok(svc.getSingleNotice(ntcId));
    }
}
