package fre.shown.tryhook.module.carousel.domain;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author Shaman
 * @date 2019/12/2 16:04
 */

@Data
public class CarouselDO {
    private Long id;
    private String chartPath;

    private Timestamp gmtCreate;
    private Timestamp gmtModified;
}
