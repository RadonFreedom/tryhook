package fre.shown.tryhook.core.book.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Shaman
 * @date 2019/12/8 16:16
 */

@Data
public class BookSearchVO {
    private Long id;
    private String name;
    private String coverPath;
    private BigDecimal price;
    private String author;
    private String publisher;
    private String publishTime;
    private String isbn;
}
