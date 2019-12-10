package fre.shown.tryhook.module.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Shaman
 * @date 2019/12/2 15:26
 */

@Getter
@AllArgsConstructor
public enum PrincipalStatusEnum {

    NOT(-1, "未认证"),
    PENDING(0, "认证中"),
    SUCCESS(1, "已认证"),
    FAILED(2, "认证失败"),
    ;

    private final int id;
    private final String desc;
}
