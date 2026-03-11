package playground.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import playground.model.entity.plain.User;

import java.util.Objects;

public class UsetUtil {
    public static User getUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof String) {
            return null;
        }

        User user = (User) principal;

        return user;
    }

    public static Long getLoginUserId() {
        return Objects.requireNonNull(getUser()).getUserId();
    }

    public static String getLoginUserName() {
        return Objects.requireNonNull(getUser()).getUserNm();
    }
}
