package fre.shown.tryhook.core.book.domain;

import lombok.Data;

/**
 * @author Shaman
 * @date 2019/12/9 14:59
 */

@Data
public class BookStarVO {

    private Long id;
    private String name;
    private String author;
    private String coverPath;
    private String introduction;
}
