package fre.shown.tryhook.web.controller;

import fre.shown.tryhook.common.domain.Result;
import fre.shown.tryhook.core.user.UserService;
import fre.shown.tryhook.core.book.domain.BookStarVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/star")
    public Result<Void> starBook(Principal principal, Long bookId) {
        return userService.addStarByUsernameAndBookId(principal.getName(), bookId);
    }

    @GetMapping("/star")
    public Result<List<BookStarVO>> getStarBooks(Principal principal) {
        return userService.getStarBooksByUsername(principal.getName());
    }
}
