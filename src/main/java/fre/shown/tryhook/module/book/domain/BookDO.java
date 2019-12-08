package fre.shown.tryhook.module.book.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author Shaman
 * @date 2019/12/2 14:48
 */

@Data
@Entity
@Table(name = "book")
public class BookDO {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Long categoryId;
    private String coverPath;
    private BigDecimal price;
    private String author;
    private String publisher;
    private String publishTime;
    private String isbn;
    private String introduction;
    private Integer statusId;

    @CreationTimestamp
    private Timestamp gmtCreate;
    @UpdateTimestamp
    private Timestamp gmtModified;
}
