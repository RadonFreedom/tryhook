package fre.shown.tryhook.module.user.manager;

import fre.shown.tryhook.common.domain.ErrorEnum;
import fre.shown.tryhook.common.domain.Result;
import fre.shown.tryhook.common.util.DataUtils;
import fre.shown.tryhook.core.user.UserVO;
import fre.shown.tryhook.module.base.ManagerHelper;
import fre.shown.tryhook.module.user.dao.UserDAO;
import fre.shown.tryhook.module.user.domain.UserDO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedList;
import java.util.List;

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
    ManagerHelper managerHelper;

    public Result<UserDO> findByUsername(String username) {
        if (!existsByUsername(username)) {
            return Result.error(ErrorEnum.PARAM_ERROR);
        }
        try {
            return Result.success(userDAO.findByUsername(username));
        } catch (Exception e) {
            logger.error(ErrorEnum.RUNTIME_ERROR.getMsg(), e);
            return Result.error(ErrorEnum.RUNTIME_ERROR);
        }
    }

    public boolean existsByUsername(String username) {
        return !StringUtils.isBlank(username) && userDAO.existsByUsername(username);
    }

    public Result<UserDO> save(UserDO userDO) {
        return managerHelper.save(userDO, userDAO);
    }


    public Result<UserDO> saveAvatar(UserDO userDO, MultipartFile avatar) {
        return managerHelper.saveWithFile(avatar, userDO.getAvatarPath(), userDO, userDAO);
    }

    public Result<List<UserVO>> pageQuery(Integer page, Integer size) {
        Result<List<UserDO>> result =  managerHelper.pageQuery(page, size, userDAO);
        if (!Result.isSuccess(result)) {
            return Result.error(result);
        }

        List<UserVO> userVOList = new LinkedList<>();
        for (UserDO userDO : result.getValue()) {
            userVOList.add(DataUtils.copyFields(userDO, new UserVO()));
        }
        return Result.success(userVOList);
    }

    public Result<Boolean> deleteUserByIds(List<Long> ids) {
        return managerHelper.deleteAllById(ids, userDAO);
    }
}
