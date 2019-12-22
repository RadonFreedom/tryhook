package fre.shown.tryhook.web;

import fre.shown.tryhook.common.util.SpringUtils;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static fre.shown.tryhook.common.constant.PathConstant.UPLOAD_PREFIX;

/**
 * @author Shaman
 * @date 2019/12/10 18:29
 */

@Controller
public class StaticController {

    @RequestMapping("/static")
    public ResponseEntity getStatic(String dir) {
        ResourceLoader resourceLoader = SpringUtils.getApplicationContext();
        // 读取本机的文件，要加上前缀 file:
        try {
            return ResponseEntity.ok(resourceLoader.getResource("file:" + UPLOAD_PREFIX + dir));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
