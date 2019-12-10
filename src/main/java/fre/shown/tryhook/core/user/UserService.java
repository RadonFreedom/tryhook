package fre.shown.tryhook.core.user;

import fre.shown.tryhook.common.domain.ErrorEnum;
import fre.shown.tryhook.common.domain.Result;
import fre.shown.tryhook.common.util.DataUtils;
import fre.shown.tryhook.core.book.domain.BookStarVO;
import fre.shown.tryhook.module.book.dao.BookDAO;
import fre.shown.tryhook.module.book.domain.BookDO;
import fre.shown.tryhook.module.user.dao.UserDAO;
import fre.shown.tryhook.module.user.domain.UserDO;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

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




