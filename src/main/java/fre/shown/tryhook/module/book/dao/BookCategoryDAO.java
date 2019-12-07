package fre.shown.tryhook.module.book.dao;

import fre.shown.tryhook.module.book.domain.BookCategoryDO;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Shaman
 * @date 2019/12/6 17:24
 */

public interface BookCategoryDAO extends PagingAndSortingRepository<BookCategoryDO, Long> {


}
