package playground.apps.logging;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

    public boolean shouldSkipPageLog(HttpServletRequest request,
                                      HttpServletResponse response) {
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


    public void savePageAcsLog(
            Long userId, Long menuNo, String userIp
    ) {
        if(userId == 1L) { // 관리자는 로깅 안함
            return;
        }

        if(menuNo == 6L) { // 홈 화면은 로깅 안함
            return;
        }

        insertPageAcsLog(
                new PageAcsLogRequestDTO(userId, menuNo, userIp)
        );
    }
}
