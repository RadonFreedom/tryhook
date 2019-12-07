package fre.shown.tryhook.module;

import fre.shown.tryhook.module.book.dao.BookCategoryDAO;
import fre.shown.tryhook.module.book.dao.BookDAO;
import fre.shown.tryhook.module.book.dao.BookVideoDAO;
import fre.shown.tryhook.module.book.domain.BookCategoryDO;
import fre.shown.tryhook.module.book.domain.BookDO;
import fre.shown.tryhook.module.book.domain.BookVideoDO;
import fre.shown.tryhook.module.book.eums.BookStatusEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Shaman
 * @date 2019/12/6 16:34
 */

@SpringBootTest
public class DAOTests {

    @Autowired
    private BookDAO bookDAO;
    @Autowired
    private BookCategoryDAO bookCategoryDAO;
    @Autowired
    private BookVideoDAO bookVideoDAO;

    @Test
    public void testBookDAO() {

        BookCategoryDO bookCategoryDO = new BookCategoryDO();
        bookCategoryDO.setDesc("asd");
        System.out.println(bookCategoryDAO.save(bookCategoryDO));
        System.out.println(bookCategoryDAO.findById(bookCategoryDO.getId()));


        BookDO bookDO = new BookDO();
        bookDO.setName("1");
        bookDO.setCategoryId(1L);
        bookDO.setCoverPath("/1");
        bookDO.setIntroduction("1");
        bookDO.setStatusId(BookStatusEnum.AVAILABLE.getId());
        bookDAO.save(bookDO);
        System.out.println(bookDO);
        bookDO.setName("2");
        bookDAO.save(bookDO);
        System.out.println(bookDAO.findById(bookDO.getId()));

        BookVideoDO bookVideoDO = new BookVideoDO();
        bookVideoDO.setBookId(1L);
        bookVideoDO.setChapterTitle("awd");
        bookVideoDO.setVideoPath("/1/");
    }
}
