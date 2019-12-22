package fre.shown.tryhook.module.user.dao;

import fre.shown.tryhook.module.base.BaseDAO;
import fre.shown.tryhook.module.user.domain.UserDO;

/**
 * @author Shaman
 * @date 2019/12/6 14:49
 */

public interface UserDAO extends BaseDAO<UserDO> {

    UserDO findByUsername(String username);

    boolean existsByUsername(String username);
}
