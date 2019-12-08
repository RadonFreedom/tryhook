package fre.shown.tryhook.core.book;

import fre.shown.tryhook.common.domain.ErrorEnum;
import fre.shown.tryhook.common.domain.Result;
import fre.shown.tryhook.core.book.domain.BookDetailVO;
import fre.shown.tryhook.core.book.domain.BookRecommendationVO;
import fre.shown.tryhook.core.book.domain.BookSearchVO;
import fre.shown.tryhook.module.book.dao.BookDAO;
import fre.shown.tryhook.module.book.dao.BookVideoDAO;
import fre.shown.tryhook.module.book.domain.BookDO;
import fre.shown.tryhook.module.book.enums.BookStatusEnum;
import fre.shown.tryhook.module.query.AvailableBookQueryManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Shaman
 * @date 2019/12/8 15:11
 */

@Service
public class BookService {

    @Autowired
    BookDAO bookDAO;
    @Autowired
    BookVideoDAO bookVideoDAO;
    @Autowired
    AvailableBookQueryManager availableBookQueryManager;
    Logger logger = LoggerFactory.getLogger(BookService.class);

    public Result<List<BookRecommendationVO>> bookRecommendationPageQuery(Integer page, Integer size) {

        if (page == null || size == null) {
            return Result.error(ErrorEnum.PARAM_ERROR);
        }
        List<BookDO> bookDOList = availableBookQueryManager.pageQuery(page, size, bookDAO);
        LinkedList<BookRecommendationVO> bookRecommendationVOList = new LinkedList<>();
        for (BookDO bookDO : bookDOList) {
            BookRecommendationVO curr = new BookRecommendationVO();
            BeanUtils.copyProperties(bookDO, curr);
            bookRecommendationVOList.addLast(curr);
        }
        return Result.success(bookRecommendationVOList);
    }

    public Result<BookDetailVO> getBookDetailById(Long id) {
        if (id == null) {
            return Result.error(ErrorEnum.PARAM_ERROR);
        }
        BookDO bookDO = bookDAO.findByIdAndStatusId(id, BookStatusEnum.AVAILABLE.getId());
        if (bookDO == null) {
            return Result.error(ErrorEnum.RESULT_EMPTY);
        }
        BookDetailVO bookDetailVO = new BookDetailVO();
        BeanUtils.copyProperties(bookDO, bookDetailVO);
        bookDetailVO.setVideos(bookVideoDAO.findAllByBookId(id));

        return Result.success(bookDetailVO);
    }

    public Result<List<BookSearchVO>> searchBooks(String keyword) {
        if (keyword == null) {
            return Result.error(ErrorEnum.PARAM_ERROR);
        }
        //TODO
        List<BookSearchVO> searchResult = new LinkedList<>();

        return Result.success(searchResult);
    }
}
