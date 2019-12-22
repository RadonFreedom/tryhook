package fre.shown.tryhook.module.book.dao;

import fre.shown.tryhook.module.base.BaseDAO;
import fre.shown.tryhook.module.book.domain.BookVideoDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Shaman
 * @date 2019/12/6 14:49
 */

public interface BookVideoDAO extends BaseDAO<BookVideoDO> {

    List<BookVideoDO> findAllByBookId(Long bookId);

    void deleteAllByBookIdIn(List<Long> bookIds);

    Page<BookVideoDO> findAllByBookId(Long bookId, Pageable pageable);
}
