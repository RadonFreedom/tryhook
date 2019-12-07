package fre.shown.tryhook.module.user.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Shaman
 * @date 2019/11/29 15:35
 */

@Data
@Entity
@Table(name = "principalCfg")
public class PrincipalCfgDO {

    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    private String phoneNumber;
    private String licensePath;
    private String kindergartenName;
    private Integer certificationStatusId;
}
