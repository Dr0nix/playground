package playground.apps.community;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import playground.model.dto.NoticeReqDto;
import playground.model.dto.NoticeResDto;
import playground.utils.UsetUtil;

import java.util.Map;

@Tag(name = "Notice", description = "공지사항 API")
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

    @PostMapping
    public ResponseEntity<?> createNotice(@RequestBody NoticeReqDto req) {
        if (!isAdmin()) {
            return new ResponseEntity<>(Map.of("error", "관리자만 작성할 수 있습니다."), HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(svc.createNotice(req), HttpStatus.CREATED);
    }

    @PutMapping("/{ntcId}")
    public ResponseEntity<?> updateNotice(@PathVariable Long ntcId, @RequestBody NoticeReqDto req) {
        if (!isAdmin()) {
            return new ResponseEntity<>(Map.of("error", "관리자만 수정할 수 있습니다."), HttpStatus.FORBIDDEN);
        }

        return ResponseEntity.ok(svc.updateNotice(ntcId, req));
    }

    @DeleteMapping("/{ntcId}")
    public ResponseEntity<?> deleteNotice(@PathVariable Long ntcId) {
        if (!isAdmin()) {
            return new ResponseEntity<>(Map.of("error", "관리자만 삭제할 수 있습니다."), HttpStatus.FORBIDDEN);
        }

        svc.deleteNotice(ntcId);
        return ResponseEntity.ok(Map.of("message", "삭제되었습니다."));
    }

    private boolean isAdmin() {
        Long userId = UsetUtil.getLoginUserId();
        return userId != null && userId == 1L;
    }
}
