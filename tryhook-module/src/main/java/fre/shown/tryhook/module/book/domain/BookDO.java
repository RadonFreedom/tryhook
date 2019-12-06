package fre.shown.tryhook.module.book.domain;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author Shaman
 * @date 2019/12/2 14:48
 */

@Data
public class BookDO {

    private Long id;
    private String name;
    private Long categoryId;
    private String coverPath;
    private String introduction;
    private Integer statusId;

    private Timestamp gmtCreate;
    private Timestamp gmtModified;
}
