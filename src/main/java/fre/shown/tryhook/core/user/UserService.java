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

    public Result<Void> addStarByUsernameAndBookId(String username, Long bookId) {

        if (StringUtils.isBlank(username) || DataUtils.isIllegal(bookId)) {
            return Result.error(ErrorEnum.PARAM_ERROR);
        }

        UserDO userDO = userDAO.findByUsername(username);
        if (userDO == null) {
            return Result.error(ErrorEnum.RESULT_EMPTY);
        }

        String star = userDO.getStar() + "," + bookId;
        userDO.setStar(star);
        userDAO.save(userDO);
        return Result.success(null);
    }

    public Result<List<BookStarVO>> getStarBooksByUsername(String username) {
        if (StringUtils.isBlank(username)) {
            return Result.error(ErrorEnum.PARAM_ERROR);
        }

        UserDO userDO = userDAO.findByUsername(username);
        if (userDO == null) {
            return Result.error(ErrorEnum.RESULT_EMPTY);
        }

        List<Long> stars = new LinkedList<>();
        for (String star : userDO.getStar().split(",")) {
            stars.add(Long.valueOf(star));
        }

        LinkedList<BookStarVO> result = new LinkedList<>();
        for (BookDO bookDO : bookDAO.findAllById(stars)) {
            result.add(DataUtils.copyFields(bookDO, new BookStarVO()));
        }
        //TODO
        return Result.success(result);
    }
}




