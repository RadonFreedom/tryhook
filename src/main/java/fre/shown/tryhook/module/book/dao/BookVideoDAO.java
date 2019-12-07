package fre.shown.tryhook.module.book.dao;

import fre.shown.tryhook.module.book.domain.BookVideoDO;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Shaman
 * @date 2019/12/6 14:49
 */

public interface BookVideoDAO extends PagingAndSortingRepository<BookVideoDO, Long> {


}
