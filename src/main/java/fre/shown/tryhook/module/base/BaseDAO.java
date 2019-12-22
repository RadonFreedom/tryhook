package fre.shown.tryhook.module.base;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author Shaman
 * @date 2019/12/13 15:27
 */

@ConditionalOnExpression("false")
public interface BaseDAO<T> extends PagingAndSortingRepository<T, Long> {

    void deleteAllByIdIn(List<Long> ids);
}
