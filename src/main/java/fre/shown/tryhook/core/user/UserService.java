package fre.shown.tryhook.core.user;

import fre.shown.tryhook.common.domain.ErrorEnum;
import fre.shown.tryhook.common.domain.Result;
import fre.shown.tryhook.common.util.DataUtils;
import fre.shown.tryhook.common.util.FileUtils;
import fre.shown.tryhook.core.book.domain.BookStarVO;
import fre.shown.tryhook.core.security.RoleEnum;
import fre.shown.tryhook.module.book.dao.BookDAO;
import fre.shown.tryhook.module.book.domain.BookDO;
import fre.shown.tryhook.module.user.domain.PrincipalCfgDO;
import fre.shown.tryhook.module.user.domain.UserDO;
import fre.shown.tryhook.module.user.enums.PrincipalStatusEnum;
import fre.shown.tryhook.module.user.manager.PrincipalCfgManager;
import fre.shown.tryhook.module.user.manager.UserManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    PrincipalCfgManager principalCfgManager;

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

    public Result<Boolean> starByUsernameAndBookId(String username, Long bookId) {

        if (StringUtils.isBlank(username) || DataUtils.isIllegal(bookId)) {
            return Result.error(ErrorEnum.PARAM_ERROR);
        }

        Result<UserDO> userDOResult = userManager.findByUsername(username);
        if (!Result.isSuccess(userDOResult)) {
            return Result.error(userDOResult);
        }
        UserDO userDO = userDOResult.getValue();

        String stars = userDO.getStar();
        boolean result;
        if (stars == null) {
            stars = "";
        }
        if (stars.contains(bookId.toString())) {
            //unstar the book
            stars = stars.replace("," + bookId, "");
            result = false;
        } else {
            //star the book
            stars += "," + bookId;
            result = true;
        }

        userDO.setStar(stars);
        Result<UserDO> saveResult = userManager.save(userDO);
        return Result.isSuccess(saveResult) ? Result.success(result) : Result.error(saveResult);
    }

    public Result<List<BookStarVO>> getStarsByUsername(String username) {
        Result<UserDO> userDOResult = userManager.findByUsername(username);
        if (!Result.isSuccess(userDOResult)) {
            return Result.error(userDOResult);
        }
        UserDO userDO = userDOResult.getValue();

        List<Long> starIds = new LinkedList<>();
        LinkedList<BookStarVO> result = new LinkedList<>();

        if (StringUtils.isEmpty(userDO.getStar())) {
            return Result.success(result);
        }

        for (String starId : userDO.getStar().split(",")) {
            if (NumberUtils.isParsable(starId)) {
                starIds.add(Long.valueOf(starId));
            }
        }

        for (BookDO bookDO : bookDAO.findAllById(starIds)) {
            result.add(DataUtils.copyFields(bookDO, new BookStarVO()));
        }

        return Result.success(result);
    }

    public Boolean isStar(Long bookId, String username) {
        Result<UserDO> userDOResult = userManager.findByUsername(username);
        if (!Result.isSuccess(userDOResult)) {
            return false;
        }
        UserDO userDO = userDOResult.getValue();
        if (userDO == null || userDO.getStar() == null) {
            return false;
        }

        return userDO.getStar().contains(bookId.toString());
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
        return userManager.deleteUserByIds(ids);
    }
}




