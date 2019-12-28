package fre.shown.tryhook.core.book;

import fre.shown.tryhook.common.constant.PathConstant;
import fre.shown.tryhook.common.domain.ErrorEnum;
import fre.shown.tryhook.common.domain.Result;
import fre.shown.tryhook.common.util.DataUtils;
import fre.shown.tryhook.common.util.FileUtils;
import fre.shown.tryhook.core.book.domain.BookDetailVO;
import fre.shown.tryhook.core.book.domain.BookRecommendationVO;
import fre.shown.tryhook.core.book.domain.BookSearchVO;
import fre.shown.tryhook.core.user.UserService;
import fre.shown.tryhook.module.base.BookQueryHelper;
import fre.shown.tryhook.module.base.ManagerHelper;
import fre.shown.tryhook.module.book.dao.BookCategoryDAO;
import fre.shown.tryhook.module.book.dao.BookDAO;
import fre.shown.tryhook.module.book.dao.BookVideoDAO;
import fre.shown.tryhook.module.book.domain.BookCategoryDO;
import fre.shown.tryhook.module.book.domain.BookDO;
import fre.shown.tryhook.module.book.domain.BookVideoDO;
import fre.shown.tryhook.module.book.enums.BookStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    BookQueryHelper bookQueryHelper;
    @Autowired
    ManagerHelper managerHelper;
    @Autowired
    UserService userService;

    public Result<List<BookRecommendationVO>> bookRecommendationPageQuery(Integer page, Integer size) {

        Result<List<BookDO>> bookDOList = bookQueryHelper.availableBookPageQuery(page, size, bookDAO);
        if (bookDOList.getValue() == null) {
            return Result.error(ErrorEnum.RESULT_EMPTY);
        }

        LinkedList<BookRecommendationVO> bookRecommendationVOList = new LinkedList<>();
        for (BookDO bookDO : bookDOList.getValue()) {
            bookRecommendationVOList.addLast(DataUtils.copyFields(bookDO, new BookRecommendationVO()));
        }
        return Result.success(bookRecommendationVOList);
    }

    public Result<BookDetailVO> getBookDetailById(Long id) {
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

        bookDetailVO.setStar(userService.isStar(id));

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

    public Result<List<BookDO>> getBook(Integer page, Integer size) {
        return managerHelper.pageQuery(page, size, bookDAO);
    }

    public Result<Boolean> saveBook(MultipartFile cover, BookDO bookDO) {
        String dir = PathConstant.BOOK_COVER_PATH_PREFIX + FileUtils.getRandomFileName(cover);
        Result<BookDO> result =  managerHelper.saveWithFile(cover, dir, bookDO, bookDAO);
        return Result.isSuccess(result) ? Result.success(true) : Result.error(result);
    }

    public Result<Boolean> deleteBookByIds(List<Long> ids) {
        bookVideoDAO.deleteAllByBookIdIn(ids);
        return managerHelper.deleteAllById(ids, bookDAO);
    }

    public Result<List<BookCategoryDO>> getBookCategory(Integer page, Integer size) {
        return managerHelper.pageQuery(page, size, bookCategoryDAO);
    }

    public Result<Boolean> saveBookCategory(BookCategoryDO bookCategoryDO) {
        Result<BookCategoryDO> result =  managerHelper.save(bookCategoryDO, bookCategoryDAO);
        return Result.isSuccess(result) ? Result.success(true) : Result.error(result);
    }

    public Result<Boolean> deleteBookCategoryByIds(List<Long> ids) {
        if (ids == null) {
            return Result.success(true);
        }
        for (Long id : ids) {
            if (bookDAO.existsByCategoryId(id)) {
                return Result.error(ErrorEnum.DATA_CONFLICT);
            }
        }
        return managerHelper.deleteAllById(ids, bookCategoryDAO);
    }

    public Result<List<BookVideoDO>> getBookVideo(Integer page, Integer size, Long bookId) {
        return bookQueryHelper.bookVideoPageQueryByBookId(page, size, bookVideoDAO, bookId);
    }

    public Result<Boolean> saveBookVideo(MultipartFile video, BookVideoDO bookVideoDO) {
        String dir = PathConstant.BOOK_VIDEO_PATH_PREFIX + FileUtils.getRandomFileName(video);
        Result<BookVideoDO> result =  managerHelper.saveWithFile(video, dir, bookVideoDO, bookVideoDAO);
        return Result.isSuccess(result) ? Result.success(true) : Result.error(result);
    }

    public Result<Boolean> deleteBookVideoByIds(List<Long> ids) {
        return managerHelper.deleteAllById(ids, bookVideoDAO);
    }
}
