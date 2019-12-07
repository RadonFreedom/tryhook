package fre.shown.tryhook.module.user.domain;

import lombok.Data;

/**
 * @author Shaman
 * @date 2019/11/29 15:35
 */

@Data
public class PrincipalCfgDO {

    private Long id;
    private Long userId;
    private String phoneNumber;
    private String licensePath;
    private String kindergartenName;
    private Integer certificationStatusId;
}
