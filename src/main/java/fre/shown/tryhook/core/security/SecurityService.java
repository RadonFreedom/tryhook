package fre.shown.tryhook.core.security;

import fre.shown.tryhook.common.domain.Result;
import fre.shown.tryhook.common.util.DataUtils;
import fre.shown.tryhook.module.user.domain.UserDO;
import fre.shown.tryhook.module.user.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Shaman
 * @date 2019/12/7 12:28
 */

@Service
public class SecurityService implements UserDetailsService {

    @Autowired
    private UserManager userManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Result<UserDO> result = userManager.findByUsername(username);
        if (!Result.isSuccess(result)) {
            throw new UsernameNotFoundException(result.getMsg());
        }

        return DataUtils.copyFields(result.getValue(), new UserDetailsImpl());
    }
}
