package playground.apps.logging;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import playground.model.dto.PageAcsLogRequestDTO;
import playground.model.dto.PageAcsLogResponseDTO;
import playground.model.mapper.PageAcsLogMapp;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class PageAcsLogSvc {
    private static final String LAST_PAGE_LOG_KEY = "LAST_PAGE_LOG_KEY";
    private static final String LAST_PAGE_LOG_AT = "LAST_PAGE_LOG_AT";
    private static final long DUPLICATE_INTERVAL_MILLIS = 5000L;

    private final PageAcsLogMapp mapp;

    public List<PageAcsLogResponseDTO> getAllPageAcsLog() {
        return mapp.getAllPageAcsLog();
    }

    public List<PageAcsLogResponseDTO> searchPageAcsLog(Map<String, Object> paramMap) {
        return mapp.searchPageAcsLog(paramMap);
    }

    private void insertPageAcsLog(PageAcsLogRequestDTO dto) {
        mapp.insertPageAcsLog(dto);
    }

    private boolean shouldSkipByCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return false;

        for (Cookie cookie : cookies) {
            if ("skipPageLogOnce".equals(cookie.getName())
                    && "Y".equals(cookie.getValue())) {

                Cookie remove = new Cookie("skipPageLogOnce", "");
                remove.setPath("/");
                remove.setMaxAge(0);
                response.addCookie(remove);

                return true;
            }
        }
        return false;
    }

    private boolean shouldSkipByRecentSession(HttpSession session, Long userId, Long menuNo) {
        long now = System.currentTimeMillis();
        String currentKey = userId + ":" + menuNo;

        String lastKey = (String) session.getAttribute(LAST_PAGE_LOG_KEY);
        Long lastAt = (Long) session.getAttribute(LAST_PAGE_LOG_AT);

        boolean shouldSkip = lastKey != null
                && lastAt != null
                && lastKey.equals(currentKey)
                && (now - lastAt) <= DUPLICATE_INTERVAL_MILLIS;

        // 매 요청마다 현재 값으로 갱신
        session.setAttribute(LAST_PAGE_LOG_KEY, currentKey);
        session.setAttribute(LAST_PAGE_LOG_AT, now);

        return shouldSkip;
    }

    public boolean shouldSkipPageLog(
            HttpServletRequest request,
            HttpServletResponse response,
            Long userId,
            Long menuNo
    ) {
        // 1) 명시적 1회 스킵 (쿠키 룰)
        if (shouldSkipByCookie(request, response)) {
            return true;
        }

        // 2) 같은 세션에서 5초 이내 동일 페이지 재진입 스킵
        if (shouldSkipByRecentSession(request.getSession(), userId, menuNo)) {
            return true;
        }

        return false;
    }


    public void savePageAcsLog(
            Long userId, Long menuNo, String userIp
    ) {
        if(userId == 1L) { // 관리자는 로깅 안함
//            return;
        }

        if(menuNo == 6L) { // 홈 화면은 로깅 안함
            return;
        }

        insertPageAcsLog(
                new PageAcsLogRequestDTO(userId, menuNo, userIp)
        );
    }
}
