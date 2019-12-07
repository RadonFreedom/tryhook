package fre.shown.tryhook.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Shaman
 * @date 2019/12/7 12:49
 */

@RestController
@PreAuthorize("hasAuthority(1)")
public class AdminController {

    @RequestMapping("/")
    public String test() {
        return "1";
    }

}
