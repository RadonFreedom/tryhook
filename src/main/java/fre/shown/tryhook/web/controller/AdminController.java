package fre.shown.tryhook.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Shaman
 * @date 2019/12/7 12:49
 */

@RestController
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping("/")
    public String test() {
        return "1";
    }
}
