package fre.shown.tryhook.common.constant;

import fre.shown.tryhook.common.util.SpringUtils;
import fre.shown.tryhook.module.book.domain.BookDO;
import fre.shown.tryhook.module.book.domain.BookVideoDO;
import fre.shown.tryhook.module.carousel.domain.CarouselDO;
import fre.shown.tryhook.module.user.domain.PrincipalCfgDO;
import fre.shown.tryhook.module.user.domain.UserDO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Shaman
 * @date 2019/12/10 14:32
 */

@Component
public class PathConstant {

    /**
     * @see PrincipalCfgDO#licensePath
     */
    public static final String PRINCIPAL_LICENSE_PATH_PREFIX = "user/license/";
    /**
     * @see UserDO#avatarPath
     */
    public static final String USER_AVATAR_PATH_PREFIX = "user/avatar/";
    public static final String DEFAULT_USER_AVATAR = USER_AVATAR_PATH_PREFIX + "default.ico";
    /**
     * @see BookDO#coverPath
     */
    public static final String BOOK_COVER_PATH_PREFIX = "book/cover/";
    /**
     * @see BookVideoDO#videoPath
     */
    public static final String BOOK_VIDEO_PATH_PREFIX = "book/video/";
    /**
     * @see CarouselDO#chartPath
     */
    public static final String CAROUSEL_PATH_PREFIX = "carousel/";


    @Value("${UPLOAD_PATH}")
    private String uploadPath;

    public static String UPLOAD_PATH() {
        return SpringUtils.getBean(PathConstant.class).uploadPath;
    }
}
