package fre.shown.tryhook.module.user.manager;

import fre.shown.tryhook.common.util.FileUtils;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Shaman
 * @date 2019/12/12 11:22
 */

@Component
public class UploadManagerHelper {

    @Transactional(rollbackFor = Throwable.class)
    public <T, D extends PagingAndSortingRepository<T, Long>> T save(MultipartFile file, String dir, T entity, D dao) throws IOException {

        FileUtils.save(file, dir);
        return dao.save(entity);
    }
}
