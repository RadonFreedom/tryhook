package fre.shown.tryhook.common.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static fre.shown.tryhook.common.constant.PathConstant.UPLOAD_PREFIX;

/**
 * @author Shaman
 * @date 2019/12/10 16:42
 */

public class FileUtils {

    public static String getRandomUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取文件后缀
     */
    public static String getSuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成新的文件名
     */
    public static String getRandomFileName(String fileOriginName) {
        return getRandomUUID() + getSuffix(fileOriginName);
    }

    public static void save(MultipartFile file, String dir) throws IOException {

        File f = new File(UPLOAD_PREFIX + dir);
        if (f.getParentFile() != null) {
            f.getParentFile().mkdirs();
        }
        f.createNewFile();
        file.transferTo(f.toPath());
    }


}
