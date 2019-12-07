package fre.shown.tryhook.module.user.domain;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author Shaman
 * @date 2019/11/29 15:35
 */

@Data
public class UserDO {

    private Long id;
    private String username;
    private String password;
    private String avatarPath;
    private Integer roleId;
    private String star;

    private Timestamp gmtCreate;
    private Timestamp gmtModified;
}
