package fre.shown.tryhook.module.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Shaman
 * @date 2019/12/2 15:09
 */

@Getter
@RequiredArgsConstructor
public enum UserRoleEnum {

    ADMIN(0, "管理员"),
    PARENT(1, "家长"),
    PRINCIPAL(2, "园长"),
    ;

    private final int id;
    private final String roleName;
}
