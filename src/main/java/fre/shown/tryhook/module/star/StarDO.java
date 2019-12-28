package fre.shown.tryhook.module.star;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Shaman
 * @date 2019/12/28 00:17
 */

@Data
@Entity
@Table(name = "star")
public class StarDO {
    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    private Long bookId;
}
