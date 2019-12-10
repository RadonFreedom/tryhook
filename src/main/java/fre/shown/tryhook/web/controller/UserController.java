package fre.shown.tryhook.web.controller;

import fre.shown.tryhook.common.domain.Result;
import fre.shown.tryhook.core.book.domain.BookStarVO;
import fre.shown.tryhook.core.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

/**
 * @author Shaman
 * @date 2019/12/9 14:58
 */

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/star")
    public Result<Boolean> starBook(Principal principal, Long bookId) {
        return userService.starByUsernameAndBookId(principal.getName(), bookId);
    }

    @RequestMapping("/stars")
    public Result<List<BookStarVO>> getStarBooks(Principal principal) {
        return userService.getStarsByUsername(principal.getName());
    }
}
