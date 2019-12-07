package fre.shown.tryhook.module.user.dao;

import fre.shown.tryhook.module.user.domain.UserDO;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Shaman
 * @date 2019/12/6 14:49
 */

public interface UserDAO extends PagingAndSortingRepository<UserDO, Long> {

    UserDO findByUsername(String username);
}
