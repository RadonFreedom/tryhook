package fre.shown.tryhook.module.book.dao;

import fre.shown.tryhook.module.book.domain.BookDO;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Shaman
 * @date 2019/12/6 14:49
 */

public interface BookDAO extends PagingAndSortingRepository<BookDO, Long> {


}
