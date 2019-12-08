package fre.shown.tryhook.module.query;

import fre.shown.tryhook.module.book.dao.BookDAO;
import fre.shown.tryhook.module.book.domain.BookDO;
import fre.shown.tryhook.module.book.enums.BookStatusEnum;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Shaman
 * @date 2019/12/8 23:20
 */

@Component
public class AvailableBookQueryManager extends QueryManager<BookDO, BookDAO> {

    @Override
    public List<BookDO> pageQuery(int page, int size, BookDAO dao) {
        return dao.findAllByStatusId(BookStatusEnum.AVAILABLE.getId(),
                PageRequest.of(page, size, SORT)).getContent();
    }
}
