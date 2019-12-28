package fre.shown.tryhook.module.star;

import fre.shown.tryhook.module.base.BaseDAO;

import java.util.List;

/**
 * @author Shaman
 * @date 2019/12/28 00:22
 */

public interface StarDAO extends BaseDAO<StarDO> {
    boolean existsByBookIdAndUserId(Long bookId, Long userId);
    void deleteByBookIdAndUserId(Long bookId, Long userId);
    List<StarDO> findAllByUserId(Long userId);
}
