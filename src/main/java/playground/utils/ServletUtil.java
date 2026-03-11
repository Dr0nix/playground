package playground.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ServletUtil {
    public static String getClientIp(HttpServletRequest request) {
        return request.getRemoteAddr();
    }
}
