package fre.shown.tryhook.core.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Shaman
 * @date 2019/12/2 15:09
 */

@Getter
@AllArgsConstructor
public enum RoleEnum {

    ADMIN(0, "管理员"),
    USER(1, "用户");

    private int id;
    private String roleName;
}
