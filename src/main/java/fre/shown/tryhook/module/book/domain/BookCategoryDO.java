package fre.shown.tryhook.module.book.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Shaman
 * @date 2019/12/2 15:59
 */

@Data
@Entity
@Table(name = "bookCategory")
public class BookCategoryDO {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "`desc`")
    private String desc;
}
