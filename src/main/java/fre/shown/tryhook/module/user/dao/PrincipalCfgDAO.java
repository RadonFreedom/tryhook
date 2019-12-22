package fre.shown.tryhook.module.user.dao;

import fre.shown.tryhook.module.base.BaseDAO;
import fre.shown.tryhook.module.user.domain.PrincipalCfgDO;

/**
 * @author Shaman
 * @date 2019/12/6 14:49
 */

public interface PrincipalCfgDAO extends BaseDAO<PrincipalCfgDO> {
    PrincipalCfgDO findByUserId(Long userId);
}
