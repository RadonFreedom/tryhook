package fre.shown.tryhook.module.book.domain;

import lombok.Data;

/**
 * @author Shaman
 * @date 2019/12/2 16:13
 */

@Data
public class BookVideoDO {

    private Long id;
    private Long bookId;
    private String chapterTitle;
    private String videoPath;
}
