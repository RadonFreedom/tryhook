package fre.shown.tryhook.core.user;

import lombok.Data;

/**
 * @author Shaman
 * @date 2019/12/13 17:36
 */

@Data
public class PrincipalVO {

    private Long id;
    private Long userId;
    private String username;
    private String phoneNumber;
    private String licensePath;
    private String kindergartenName;
    private Integer certificationStatusId;
}
