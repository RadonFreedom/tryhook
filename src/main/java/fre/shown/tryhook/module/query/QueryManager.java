package fre.shown.tryhook.module.query;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Shaman
 * @date 2019/12/8 15:45
 */

public interface QueryManager<E, D extends PagingAndSortingRepository<E, Long>> {

    Sort SORT = Sort.by(Sort.Order.desc("id"));

    default List<E> pageQuery(int page, int size, D dao) {
        return dao.findAll(PageRequest.of(page, size, SORT)).getContent();
    }
}
