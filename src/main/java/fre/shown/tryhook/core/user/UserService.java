package fre.shown.tryhook.core.user;

import fre.shown.tryhook.common.domain.ErrorEnum;
import fre.shown.tryhook.common.domain.Result;
import fre.shown.tryhook.common.util.DataUtils;
import fre.shown.tryhook.common.util.FileUtils;
import fre.shown.tryhook.core.book.domain.BookStarVO;
import fre.shown.tryhook.module.book.dao.BookDAO;
import fre.shown.tryhook.module.book.domain.BookDO;
import fre.shown.tryhook.module.user.dao.UserDAO;
import fre.shown.tryhook.module.user.domain.PrincipalCfgDO;
import fre.shown.tryhook.module.user.domain.UserDO;
import fre.shown.tryhook.module.user.enums.PrincipalStatusEnum;
import fre.shown.tryhook.module.user.manager.PrincipalCfgManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static fre.shown.tryhook.common.constant.PathConstant.PRINCIPAL_LICENSE_PATH_PREFIX;

/**
 * @author Shaman
 * @date 2019/12/10 10:49
 */

@Service
public class UserService {

    @Autowired
    UserDAO userDAO;
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

        UserDO userDO = userDAO.findByUsername(username);
        if (userDO == null) {
            return Result.error(ErrorEnum.RESULT_EMPTY);
        }

        // 增加PrincipalCfgDO
        PrincipalCfgDO principalCfgDO = new PrincipalCfgDO();
        principalCfgDO.setUserId(userDO.getId());
        principalCfgDO.setPhoneNumber(phoneNumber);
        String path = PRINCIPAL_LICENSE_PATH_PREFIX + userDO.getId().toString()
                + "/" + FileUtils.getRandomFileName(license.getOriginalFilename());
        principalCfgDO.setLicensePath(path);
        principalCfgDO.setCertificationStatusId(PrincipalStatusEnum.PENDING.getId());

        try {
            principalCfgManager.addPrincipal(principalCfgDO, license);
        } catch (IOException e) {
            logger.error(ErrorEnum.RUNTIME_ERROR.getMsg(), e);
            return Result.error(ErrorEnum.RUNTIME_ERROR);
        }
        return Result.success(true);
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

        for (PrincipalStatusEnum status : PrincipalStatusEnum.values()) {
            if (status.getId() == result.getValue().getCertificationStatusId()) {
                return Result.success(status.getId());
            }
        }
        return Result.success(PrincipalStatusEnum.NOT.getId());
    }

    public Result<PrincipalCfgDO> getPrincipal(String username) {
        return principalCfgManager.findByUsername(username);
    }

    public Result<Boolean> starByUsernameAndBookId(String username, Long bookId) {

        if (StringUtils.isBlank(username) || DataUtils.isIllegal(bookId)) {
            return Result.error(ErrorEnum.PARAM_ERROR);
        }

        UserDO userDO = userDAO.findByUsername(username);
        if (userDO == null) {
            return Result.error(ErrorEnum.RESULT_EMPTY);
        }

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
        userDAO.save(userDO);
        return Result.success(result);
    }

    public Result<List<BookStarVO>> getStarsByUsername(String username) {
        if (StringUtils.isBlank(username)) {
            return Result.error(ErrorEnum.PARAM_ERROR);
        }

        UserDO userDO = userDAO.findByUsername(username);
        if (userDO == null) {
            return Result.error(ErrorEnum.RESULT_EMPTY);
        }

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
        if (StringUtils.isBlank(username)) {
            return false;
        }

        UserDO userDO = userDAO.findByUsername(username);
        if (userDO == null || userDO.getStar() == null) {
            return false;
        }

        return userDO.getStar().contains(bookId.toString());
    }
}




