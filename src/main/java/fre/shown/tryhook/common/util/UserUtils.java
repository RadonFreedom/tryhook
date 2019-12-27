package fre.shown.tryhook.common.util;

import fre.shown.tryhook.common.domain.ErrorEnum;
import fre.shown.tryhook.core.security.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Shaman
 * @date 2019/12/27 17:42
 */

public class UserUtils {
    private static final Logger logger = LoggerFactory.getLogger(UserUtils.class);

    public static UserDetailsImpl getUserDetails() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getClass().equals(UsernamePasswordAuthenticationToken.class)) {
                UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) authentication;
                Object principal = authenticationToken.getPrincipal();
                if (principal != null && principal.getClass().equals(UserDetailsImpl.class)) {
                    return (UserDetailsImpl) principal;
                }
            }
        } catch (Exception e) {
            logger.error(ErrorEnum.RUNTIME_ERROR.getMsg(), e);
        }
        return null;
    }

    public static Long getUserId() {
        UserDetailsImpl userDetails = getUserDetails();
        if (userDetails != null) {
            return userDetails.getId();
        } else {
            return null;
        }
    }
}
