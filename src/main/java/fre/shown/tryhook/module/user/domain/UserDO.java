package fre.shown.tryhook.module.user.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * @author Shaman
 * @date 2019/11/29 15:35
 */

@Data
@Entity
@Table(name = "user")
public class UserDO {

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;
    private String avatarPath;
    private Integer roleId;
    private String star;

    @CreationTimestamp
    private Timestamp gmtCreate;
    @UpdateTimestamp
    private Timestamp gmtModified;
}
