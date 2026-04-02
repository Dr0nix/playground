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

    public static int getUserPoint() {
        User user = getUser();
        if (user == null || user.getUserPoint() == null) return 0;
        return user.getUserPoint();
    }

    public static void addPoint(int amount) {
        Objects.requireNonNull(getUser()).addPoint(amount);
    }

    public static void deductPoint(int amount) {
        Objects.requireNonNull(getUser()).deductPoint(amount);
    }
}
