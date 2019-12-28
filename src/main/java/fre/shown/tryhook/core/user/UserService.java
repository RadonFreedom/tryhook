package fre.shown.tryhook.core.user;

import fre.shown.tryhook.common.domain.ErrorEnum;
import fre.shown.tryhook.common.domain.Result;
import fre.shown.tryhook.common.util.DataUtils;
import fre.shown.tryhook.common.util.FileUtils;
import fre.shown.tryhook.common.util.UserUtils;
import fre.shown.tryhook.core.book.domain.BookStarVO;
import fre.shown.tryhook.core.security.RoleEnum;
import fre.shown.tryhook.module.book.dao.BookDAO;
import fre.shown.tryhook.module.book.domain.BookDO;
import fre.shown.tryhook.module.star.StarDAO;
import fre.shown.tryhook.module.star.StarDO;
import fre.shown.tryhook.module.user.dao.PrincipalCfgDAO;
import fre.shown.tryhook.module.user.domain.PrincipalCfgDO;
import fre.shown.tryhook.module.user.domain.UserDO;
import fre.shown.tryhook.module.user.enums.PrincipalStatusEnum;
import fre.shown.tryhook.module.user.manager.PrincipalCfgManager;
import fre.shown.tryhook.module.user.manager.UserManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedList;
import java.util.List;

import static fre.shown.tryhook.common.constant.PathConstant.*;

/**
 * @author Shaman
 * @date 2019/12/10 10:49
 */

@Service
public class UserService {

    @Autowired
    UserManager userManager;
    @Autowired
    BookDAO bookDAO;
    @Autowired
    StarDAO starDAO;
    @Autowired
    PrincipalCfgManager principalCfgManager;
    @Autowired
    PrincipalCfgDAO principalCfgDAO;

    Logger logger = LoggerFactory.getLogger(UserService.class);

    public Result<Boolean> registerPrincipal(MultipartFile license, String phoneNumber, String username) {
        if (license == null || license.isEmpty()
                || StringUtils.isEmpty(phoneNumber) || StringUtils.isEmpty(username)) {
            return Result.error(ErrorEnum.PARAM_ERROR);
        }

        Result<UserDO> result = userManager.findByUsername(username);
        if (!Result.isSuccess(result)) {
            return Result.error(result);
        }
        UserDO userDO = result.getValue();

        // 增加PrincipalCfgDO
        PrincipalCfgDO principalCfgDO = new PrincipalCfgDO();
        principalCfgDO.setUserId(userDO.getId());
        principalCfgDO.setPhoneNumber(phoneNumber);
        String path = PRINCIPAL_LICENSE_PATH_PREFIX + userDO.getId().toString()
                + "/" + FileUtils.getRandomFileName(license);
        principalCfgDO.setLicensePath(path);
        principalCfgDO.setCertificationStatusId(PrincipalStatusEnum.PENDING.getId());

        Result<PrincipalCfgDO> saveResult = principalCfgManager.addPrincipal(principalCfgDO, license);
        return Result.isSuccess(saveResult) ? Result.success(true) : Result.error(saveResult);
    }

    public Result<Integer> getPrincipalStatus(String username) {

        Result<PrincipalCfgDO> result = principalCfgManager.findByUsername(username);
        if (!Result.isSuccess(result)) {
            if (result.getCode() == ErrorEnum.RESULT_EMPTY.getCode()) {
                return Result.success(PrincipalStatusEnum.NOT.getId());
            } else {
                return Result.error(result);
            }
        }

        return Result.success(result.getValue().getCertificationStatusId());
    }

    public Result<PrincipalCfgDO> getPrincipal(String username) {
        return principalCfgManager.findByUsername(username);
    }

    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> starByBookIdAndUserId(Long bookId, Long userId) {
        if (DataUtils.isIllegal(bookId) || DataUtils.isIllegal(userId)) {
            return Result.error(ErrorEnum.PARAM_ERROR);
        }

        try {
            if (starDAO.existsByBookIdAndUserId(bookId, userId)) {
                starDAO.deleteByBookIdAndUserId(bookId, userId);
                return Result.success(false);
            } else {
                StarDO starDO = new StarDO();
                starDO.setBookId(bookId);
                starDO.setUserId(userId);
                starDAO.save(starDO);
                return Result.success(true);
            }
        } catch (Exception e) {
            logger.error(ErrorEnum.RUNTIME_ERROR.getMsg(), e);
            return Result.error(ErrorEnum.RUNTIME_ERROR);
        }
    }

    public Result<List<BookStarVO>> getStarsByUserId(Long userId) {

        if (DataUtils.isIllegal(userId)) {
            return Result.error(ErrorEnum.PARAM_ERROR);
        }

        List<StarDO> starDOList = starDAO.findAllByUserId(userId);

        List<Long> starBookIds = new LinkedList<>();
        LinkedList<BookStarVO> result = new LinkedList<>();

        if (starDOList == null || starDOList.isEmpty()) {
            return Result.success(result);
        }
        for (StarDO starDO : starDOList) {
            starBookIds.add(starDO.getBookId());
        }

        for (BookDO bookDO : bookDAO.findAllById(starBookIds)) {
            result.add(DataUtils.copyFields(bookDO, new BookStarVO()));
        }

        return Result.success(result);
    }

    public Boolean isStar(Long bookId) {
        Long userId = UserUtils.getUserId();
        return DataUtils.isLegal(userId) && starDAO.existsByBookIdAndUserId(bookId, userId);
    }

    public Result<Boolean> registerUser(String username, String password) {

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)
                || userManager.existsByUsername(username)) {
            return Result.error(ErrorEnum.PARAM_ERROR);
        }
        UserDO userDO = new UserDO();
        userDO.setUsername(username);
        userDO.setPassword(password);
        userDO.setAvatarPath(DEFAULT_USER_AVATAR);
        userDO.setRoleId(RoleEnum.USER.getId());

        Result<UserDO> saveResult = userManager.save(userDO);
        return Result.isSuccess(saveResult) ? Result.success(true) : Result.error(saveResult);
    }

    public Result<Boolean> uploadAvatar(MultipartFile avatar, String username) {
        if (avatar == null || avatar.isEmpty() || StringUtils.isBlank(username)) {
            return Result.error(ErrorEnum.PARAM_ERROR);
        }

        Result<UserDO> userDOResult = userManager.findByUsername(username);
        if (!Result.isSuccess(userDOResult)) {
            return Result.error(userDOResult);
        }
        UserDO userDO = userDOResult.getValue();

        userDO.setAvatarPath(USER_AVATAR_PATH_PREFIX + userDO.getId() + "/"
                + FileUtils.getRandomFileName(avatar));


        Result<UserDO> saveResult = userManager.saveAvatar(userDO, avatar);
        return Result.isSuccess(saveResult) ? Result.success(true) : Result.error(saveResult);
    }

    public Result<UserVO> getUserByUsername(String username) {
        Result<UserDO> userDOResult = userManager.findByUsername(username);
        if (!Result.isSuccess(userDOResult)) {
            return Result.error(userDOResult);
        }
        UserDO userDO = userDOResult.getValue();
        return Result.success(DataUtils.copyFields(userDO, new UserVO()));
    }


    public Result<List<UserVO>> getUser(Integer page, Integer size) {
        return userManager.pageQuery(page, size);
    }

    public Result<Boolean> deleteUserByIds(List<Long> ids) {
        principalCfgDAO.deleteAllByUserIdIn(ids);
        return userManager.deleteUserByIds(ids);
    }


    public Result<List<PrincipalVO>> getPrincipal(Integer page, Integer size) {

        return principalCfgManager.pageQuery(page, size);
    }

    public Result<Boolean> savePrincipal(PrincipalCfgDO principalCfgDO) {
        Result<PrincipalCfgDO> saveResult = principalCfgManager.save(principalCfgDO);
        return Result.isSuccess(saveResult) ? Result.success(true) : Result.error(saveResult);
    }

    public Result<Boolean> deletePrincipalByIds(List<Long> ids) {
        return principalCfgManager.deletePrincipal(ids);
    }
}




