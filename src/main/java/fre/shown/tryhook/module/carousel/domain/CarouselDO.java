package fre.shown.tryhook.module.carousel.domain;

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
 * @date 2019/12/2 16:04
 */

@Data
@Entity
@Table(name = "carousel")
public class CarouselDO {

    @Id
    @GeneratedValue
    private Long id;
    private String chartPath;

    @CreationTimestamp
    private Timestamp gmtCreate;
    @UpdateTimestamp
    private Timestamp gmtModified;
}
