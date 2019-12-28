package fre.shown.tryhook.common.util;

import org.springframework.beans.BeanUtils;

/**
 * @author Shaman
 * @date 2019/12/10 11:06
 */

public class DataUtils {

    public static boolean isIllegal(Long id) {
        return id == null || id <= 0;
    }

    public static boolean isLegal(Long id) {
        return id != null && id > 0;
    }

    public static <T> T copyFields(Object source, T target) {
        BeanUtils.copyProperties(source, target);
        return target;
    }
}
