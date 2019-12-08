package fre.shown.tryhook.module.book.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Shaman
 * @date 2019/12/2 16:13
 */

@Data
@Entity
@Table(name = "bookVideo")
public class BookVideoDO {

    @Id
    @GeneratedValue
    private Long id;
    private Long bookId;
    private Integer chapterNum;
    private String chapterTitle;
    private String videoPath;
}
