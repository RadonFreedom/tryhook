package fre.shown.tryhook.core.security;

import fre.shown.tryhook.common.domain.ErrorEnum;
import fre.shown.tryhook.common.util.DataUtils;
import fre.shown.tryhook.module.user.dao.UserDAO;
import fre.shown.tryhook.module.user.domain.UserDO;
import org.apache.commons.lang3.StringUtils;
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
    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if (StringUtils.isBlank(s)) {
            throw new UsernameNotFoundException(ErrorEnum.PARAM_ERROR.getMsg());
        }
        UserDO userDO = userDAO.findByUsername(s);
        if (userDO == null) {
            throw new UsernameNotFoundException(ErrorEnum.RESULT_EMPTY.getMsg());
        }

        UserDetailsImpl userDetails = new UserDetailsImpl();
        DataUtils.copyFields(userDO, userDetails);
        return userDetails;
    }
}
