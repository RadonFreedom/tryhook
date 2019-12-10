package fre.shown.tryhook.module.user.manager;

import fre.shown.tryhook.common.domain.ErrorEnum;
import fre.shown.tryhook.common.domain.Result;
import fre.shown.tryhook.common.util.FileUtils;
import fre.shown.tryhook.module.user.dao.PrincipalCfgDAO;
import fre.shown.tryhook.module.user.dao.UserDAO;
import fre.shown.tryhook.module.user.domain.PrincipalCfgDO;
import fre.shown.tryhook.module.user.domain.UserDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Shaman
 * @date 2019/12/10 17:05
 */

@Component
public class PrincipalCfgManager {
    @Autowired
    PrincipalCfgDAO principalCfgDAO;
    @Autowired
    UserDAO userDAO;

    @Transactional(rollbackFor = Throwable.class)
    public void addPrincipal(PrincipalCfgDO principalCfgDO, MultipartFile license) throws IOException {

        // 增加PrincipalCfg
        principalCfgDAO.save(principalCfgDO);
        //传输照片
        FileUtils.save(license, principalCfgDO.getLicensePath());
    }

    public Result<PrincipalCfgDO> findByUsername(String username) {
        if (StringUtils.isEmpty(username)) {
            return Result.error(ErrorEnum.PARAM_ERROR);
        }

        UserDO userDO = userDAO.findByUsername(username);
        if (userDO == null) {
            return Result.error(ErrorEnum.RESULT_EMPTY);
        }
        return Result.success(principalCfgDAO.findByUserId(userDO.getId()));
    }
}
