package fre.shown.tryhook.core.book;

import fre.shown.tryhook.common.domain.ErrorEnum;
import fre.shown.tryhook.common.domain.Result;
import fre.shown.tryhook.common.util.DataUtils;
import fre.shown.tryhook.core.book.domain.BookDetailVO;
import fre.shown.tryhook.core.book.domain.BookRecommendationVO;
import fre.shown.tryhook.core.book.domain.BookSearchVO;
import fre.shown.tryhook.module.book.dao.BookCategoryDAO;
import fre.shown.tryhook.module.book.dao.BookDAO;
import fre.shown.tryhook.module.book.dao.BookVideoDAO;
import fre.shown.tryhook.module.book.domain.BookCategoryDO;
import fre.shown.tryhook.module.book.domain.BookDO;
import fre.shown.tryhook.module.book.enums.BookStatusEnum;
import fre.shown.tryhook.module.query.AvailableBookQueryManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    BookCategoryDAO bookCategoryDAO;
    @Autowired
    BookDAO bookDAO;
    @Autowired
    BookVideoDAO bookVideoDAO;
    @Autowired
    AvailableBookQueryManager availableBookQueryManager;
    Logger logger = LoggerFactory.getLogger(BookService.class);

    public Result<List<BookRecommendationVO>> bookRecommendationPageQuery(Integer page, Integer size) {

        if (page == null || size == null || page < 0 || size <= 0) {
            return Result.error(ErrorEnum.PARAM_ERROR);
        }

        List<BookDO> bookDOList = availableBookQueryManager.pageQuery(page, size, bookDAO);
        if (bookDOList == null) {
            return Result.error(ErrorEnum.RESULT_EMPTY);
        }

        LinkedList<BookRecommendationVO> bookRecommendationVOList = new LinkedList<>();
        for (BookDO bookDO : bookDOList) {
            bookRecommendationVOList.addLast(DataUtils.copyFields(bookDO, new BookRecommendationVO()));
        }
        return Result.success(bookRecommendationVOList);
    }

    public Result<BookDetailVO> getBookDetailById(Long id) {
        //TODO star
        if (DataUtils.isIllegal(id)) {
            return Result.error(ErrorEnum.PARAM_ERROR);
        }
        BookDO bookDO = bookDAO.findByIdAndStatusId(id, BookStatusEnum.AVAILABLE.getId());
        if (bookDO == null) {
            return Result.error(ErrorEnum.RESULT_EMPTY);
        }
        BookDetailVO bookDetailVO = new BookDetailVO();
        DataUtils.copyFields(bookDO, bookDetailVO);
        bookDetailVO.setVideos(bookVideoDAO.findAllByBookId(id));

        return Result.success(bookDetailVO);
    }

    public Result<List<BookSearchVO>> searchBooks(String keyword) {
        if (keyword == null) {
            return Result.error(ErrorEnum.PARAM_ERROR);
        }

        List<BookDO> bookDOList = bookDAO.findAllByNameContains(keyword);
        if (bookDOList == null) {
            return Result.error(ErrorEnum.RESULT_EMPTY);
        }

        List<BookSearchVO> searchResult = new LinkedList<>();
        for (BookDO bookDO : bookDOList) {
            searchResult.add(DataUtils.copyFields(bookDO, new BookSearchVO()));
        }
        return Result.success(searchResult);
    }

    public Result<List<BookCategoryDO>> getBookCategories() {
        List<BookCategoryDO> bookCategoryDOList = new LinkedList<>();
        for (BookCategoryDO bookCategoryDO : bookCategoryDAO.findAll()) {
            bookCategoryDOList.add(bookCategoryDO);
        }

        return Result.success(bookCategoryDOList);
    }

    public Result<List<BookRecommendationVO>> getBooksByCategoryId(Long categoryId) {
        if (DataUtils.isIllegal(categoryId)) {
            return Result.error(ErrorEnum.PARAM_ERROR);
        }

        List<BookDO> bookList =
                bookDAO.findAllByCategoryIdAndStatusId(categoryId, BookStatusEnum.AVAILABLE.getId());
        if (bookList == null) {
            return Result.error(ErrorEnum.RESULT_EMPTY);
        }

        List<BookRecommendationVO> result = new LinkedList<>();
        for (BookDO bookDO : bookList) {
            result.add(DataUtils.copyFields(bookDO, new BookRecommendationVO()));
        }

        return Result.success(result);
    }
}
