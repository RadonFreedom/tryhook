package fre.shown.tryhook.common.util;

import fre.shown.tryhook.common.constant.PathConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

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
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成新的文件名
     */
    public static String getRandomFileName(MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.getOriginalFilename() == null) {
            return getRandomUUID();
        }
        return getRandomUUID() + getSuffix(multipartFile.getOriginalFilename());
    }

    public static void save(MultipartFile file, String dir) throws IOException {

        if (file == null || file.isEmpty() || StringUtils.isBlank(dir)) {
            return;
        }
        File f = new File(PathConstant.UPLOAD_PATH() + dir);
        if (f.getParentFile() != null) {
            f.getParentFile().mkdirs();
        }
        f.createNewFile();
        file.transferTo(f);
    }


}
