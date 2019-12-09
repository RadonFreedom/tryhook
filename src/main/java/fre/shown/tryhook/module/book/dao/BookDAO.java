package fre.shown.tryhook.module.book.dao;

import fre.shown.tryhook.module.book.domain.BookDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author Shaman
 * @date 2019/12/6 14:49
 */

public interface BookDAO extends PagingAndSortingRepository<BookDO, Long> {

    Page<BookDO> findAllByStatusId(Integer statusId, Pageable pageable);

    BookDO findByIdAndStatusId(Long id, Integer statusId);

    List<BookDO> findAllByNameContains(String keyword);

    List<BookDO> findAllByCategoryIdAndStatusId(Long id, Integer statusId);
}
