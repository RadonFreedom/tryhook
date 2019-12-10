package fre.shown.tryhook.common.util;

import org.springframework.context.ConfigurableApplicationContext;

import static fre.shown.tryhook.Application.AC;

/**
 * @author Shaman
 * @date 2019/12/8 14:48
 */

public class SpringUtils {
    public static <T> T getBean(Class<T> requiredType) {
        return AC.getBean(requiredType);
    }

    public static Object getBean(String name) {
        return AC.getBean(name);
    }

    public static <T> T getBean(String name, Class<T> requiredType) {
        return AC.getBean(name, requiredType);
    }

    public static ConfigurableApplicationContext getApplicationContext() {
        return AC;
    }
}
