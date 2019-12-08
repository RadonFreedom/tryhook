package fre.shown.tryhook.core.book.domain;

import fre.shown.tryhook.module.book.domain.BookVideoDO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Shaman
 * @date 2019/12/8 16:22
 */

@Data
public class BookDetailVO {

    private Long id;
    private String name;
    private String coverPath;
    private BigDecimal price;
    private String author;
    private String publisher;
    private String publishTime;
    private String isbn;
    private String introduction;
    private List<BookVideoDO> videos;
}
