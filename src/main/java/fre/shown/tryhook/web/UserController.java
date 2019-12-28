package fre.shown.tryhook.web;

import fre.shown.tryhook.common.domain.Result;
import fre.shown.tryhook.core.book.domain.BookStarVO;
import fre.shown.tryhook.core.security.UserDetailsImpl;
import fre.shown.tryhook.core.user.UserService;
import fre.shown.tryhook.core.user.UserVO;
import fre.shown.tryhook.module.user.domain.PrincipalCfgDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public Result<Boolean> starBook(@RequestParam Long bookId, @RequestAttribute UserDetailsImpl userDetails) {
        return userService.starByBookIdAndUserId(bookId, userDetails.getId());
    }

    @RequestMapping("/stars")
    public Result<List<BookStarVO>> getStarBooks(@RequestAttribute UserDetailsImpl userDetails) {
        return userService.getStarsByUserId(userDetails.getId());
    }

    @PostMapping(value = "/principal", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<Boolean> registerPrincipal(MultipartFile license, String phoneNumber, Principal principal) {
        return userService.registerPrincipal(license, phoneNumber, principal.getName());
    }

    @GetMapping("/principal/status")
    public Result<Integer> getPrincipalStatus(Principal principal) {
        return userService.getPrincipalStatus(principal.getName());
    }

    @GetMapping("/principal")
    public Result<PrincipalCfgDO> getPrincipal(Principal principal) {
        return userService.getPrincipal(principal.getName());
    }

    @PostMapping("/register")
    public Result<Boolean> register(String username, String password) {
        return userService.registerUser(username, password);
    }

    @PostMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<Boolean> uploadAvatar(MultipartFile avatar, Principal principal) {
        return userService.uploadAvatar(avatar, principal.getName());
    }

    @GetMapping("/user")
    public Result<UserVO> getUser(Principal principal) {
        return userService.getUserByUsername(principal.getName());
    }
}
