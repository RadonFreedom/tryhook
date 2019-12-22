package fre.shown.tryhook.core.user;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author Shaman
 * @date 2019/12/13 17:36
 */

@Data
public class PrincipalVO {

    private Long id;
    private String username;
    private String avatarPath;
    private String phoneNumber;
    private String licensePath;
    private String kindergartenName;
    private Integer certificationStatusId;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;
}
