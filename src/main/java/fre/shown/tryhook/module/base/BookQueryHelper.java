package fre.shown.tryhook.module.base;

import fre.shown.tryhook.common.domain.ErrorEnum;
import fre.shown.tryhook.common.domain.Result;
import fre.shown.tryhook.module.book.dao.BookDAO;
import fre.shown.tryhook.module.book.dao.BookVideoDAO;
import fre.shown.tryhook.module.book.domain.BookDO;
import fre.shown.tryhook.module.book.domain.BookVideoDO;
import fre.shown.tryhook.module.book.enums.BookStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

import static fre.shown.tryhook.module.base.ManagerHelper.SORT;

/**
 * @author Shaman
 * @date 2019/12/8 23:20
 */

@Component
public class BookQueryHelper {

    Logger logger = LoggerFactory.getLogger(BookQueryHelper.class);

    public Result<List<BookDO>> availableBookPageQuery(Integer page, Integer size, BookDAO dao) {
        if (page == null || size == null || page < 0 || size <= 0) {
            return Result.error(ErrorEnum.PARAM_ERROR);
        }
        try {
            return Result.success(dao.findAllByStatusId(BookStatusEnum.AVAILABLE.getId(),
                    PageRequest.of(page, size, SORT)).getContent());
        } catch (Exception e) {
            logger.error(ErrorEnum.RUNTIME_ERROR.getMsg(), e);
            return Result.error(ErrorEnum.RUNTIME_ERROR);
        }
    }

    public Result<List<BookVideoDO>> bookVideoPageQueryByBookId(Integer page, Integer size, BookVideoDAO dao, Long bookId) {
        if (page == null || size == null || page < 0 || size <= 0) {
            return Result.error(ErrorEnum.PARAM_ERROR);
        }
        try {
            return Result.success(dao.findAllByBookId(bookId, PageRequest.of(page, size, SORT)).getContent());
        } catch (Exception e) {
            logger.error(ErrorEnum.RUNTIME_ERROR.getMsg(), e);
            return Result.error(ErrorEnum.RUNTIME_ERROR);
        }
    }
}
