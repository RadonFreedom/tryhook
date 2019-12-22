package fre.shown.tryhook.module.user.manager;

import fre.shown.tryhook.common.domain.ErrorEnum;
import fre.shown.tryhook.common.domain.Result;
import fre.shown.tryhook.common.util.DataUtils;
import fre.shown.tryhook.core.user.PrincipalVO;
import fre.shown.tryhook.module.base.ManagerHelper;
import fre.shown.tryhook.module.user.dao.PrincipalCfgDAO;
import fre.shown.tryhook.module.user.dao.UserDAO;
import fre.shown.tryhook.module.user.domain.PrincipalCfgDO;
import fre.shown.tryhook.module.user.domain.UserDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * @author Shaman
 * @date 2019/12/10 17:05
 */

@Component
public class PrincipalCfgManager {

    @Autowired
    PrincipalCfgDAO principalCfgDAO;
    @Autowired
    UserManager userManager;
    @Autowired
    ManagerHelper managerHelper;
    @Autowired
    UserDAO userDAO;

    Logger logger = LoggerFactory.getLogger(PrincipalCfgManager.class);

    public Result<PrincipalCfgDO> addPrincipal(PrincipalCfgDO principalCfgDO, MultipartFile license) {
        return managerHelper.saveWithFile(license, principalCfgDO.getLicensePath(), principalCfgDO, principalCfgDAO);
    }

    public Result<PrincipalCfgDO> findByUsername(String username) {
        Result<UserDO> userDOResult = userManager.findByUsername(username);
        if (!Result.isSuccess(userDOResult)) {
            return Result.error(userDOResult);
        }
        try {
            return Result.success(principalCfgDAO.findByUserId(userDOResult.getValue().getId()));
        } catch (Exception e) {
            logger.error(ErrorEnum.RUNTIME_ERROR.getMsg(), e);
            return Result.error(ErrorEnum.RUNTIME_ERROR);
        }
    }

    public Result<Boolean> deletePrincipal(List<Long> ids) {
        return managerHelper.deleteAllById(ids, principalCfgDAO);
    }

    public Result<List<PrincipalVO>> pageQuery(Integer page, Integer size) {
        Result<List<PrincipalCfgDO>> result = managerHelper.pageQuery(page, size, principalCfgDAO);
        if (!Result.isSuccess(result)) {
            return Result.error(result);
        }

        List<PrincipalVO> principalVOList = new LinkedList<>();
        for (PrincipalCfgDO principalCfgDO : result.getValue()) {
            PrincipalVO principalVO = DataUtils.copyFields(principalCfgDO, new PrincipalVO());
            Optional<UserDO> userDO = userDAO.findById(principalCfgDO.getUserId());
            userDO.ifPresent(user -> principalVO.setUsername(user.getUsername()));
            principalVOList.add(principalVO);

        }
        return Result.success(principalVOList);
    }

    public Result<PrincipalCfgDO> save(PrincipalCfgDO principalCfgDO) {
        return managerHelper.save(principalCfgDO, principalCfgDAO);
    }
}
