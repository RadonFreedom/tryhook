package fre.shown.tryhook.module.user.manager;

import fre.shown.tryhook.common.domain.ErrorEnum;
import fre.shown.tryhook.common.domain.Result;
import fre.shown.tryhook.module.user.dao.UserDAO;
import fre.shown.tryhook.module.user.domain.UserDO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Shaman
 * @date 2019/12/12 10:31
 */

@Component
public class UserManager {

    Logger logger = LoggerFactory.getLogger(UserManager.class);

    @Autowired
    UserDAO userDAO;
    @Autowired
    UploadManagerHelper managerHelper;

    public Result<UserDO> findByUsername(String username) {
        if (!existsByUsername(username)) {
            return Result.error(ErrorEnum.PARAM_ERROR);
        }

        return Result.success(userDAO.findByUsername(username));
    }

    public boolean existsByUsername(String username) {
        return !StringUtils.isBlank(username) && userDAO.existsByUsername(username);
    }

    public Result<UserDO> save(UserDO userDO) {
        UserDO saveResult;
        try {
            saveResult = userDAO.save(userDO);
        } catch (Exception e) {
            logger.error(ErrorEnum.RUNTIME_ERROR.getMsg(), e);
            return Result.error(ErrorEnum.RUNTIME_ERROR);
        }

        return Result.success(saveResult);
    }


    public Result<UserDO> saveAvatar(UserDO userDO, MultipartFile avatar) {
        try {
            return Result.success(
                    managerHelper.save(avatar, userDO.getAvatarPath(), userDO, userDAO));
        } catch (IOException e) {
            logger.error(ErrorEnum.RUNTIME_ERROR.getMsg(), e);
            return Result.error(ErrorEnum.RUNTIME_ERROR);
        }


    }
}
