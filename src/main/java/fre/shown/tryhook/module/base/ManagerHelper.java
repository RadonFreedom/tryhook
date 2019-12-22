package fre.shown.tryhook.module.base;

import fre.shown.tryhook.common.domain.ErrorEnum;
import fre.shown.tryhook.common.domain.Result;
import fre.shown.tryhook.common.util.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Shaman
 * @date 2019/12/8 15:45
 */

@Component
public class ManagerHelper {

    public static final Sort SORT = Sort.by(Sort.Order.desc("id"));
    Logger logger = LoggerFactory.getLogger(ManagerHelper.class);

    public <E, D extends BaseDAO<E>> Result<List<E>> pageQuery(Integer page, Integer size, D dao) {
        if (page == null || size == null || page < 0 || size <= 0 || dao == null) {
            return Result.error(ErrorEnum.PARAM_ERROR);
        }
        try {
            return Result.success(dao.findAll(PageRequest.of(page, size, SORT)).getContent());
        } catch (Exception e) {
            logger.error(ErrorEnum.RUNTIME_ERROR.getMsg(), e);
            return Result.error(ErrorEnum.RUNTIME_ERROR);
        }
    }

    public <E, D extends BaseDAO<E>> Result<Boolean> deleteAllById(List<Long> ids, D dao) {
        if (dao == null) {
            return Result.error(ErrorEnum.PARAM_ERROR);
        }
        if (ids == null || ids.isEmpty()) {
            return Result.success(true);
        }
        try {
            dao.deleteAllByIdIn(ids);
        } catch (Exception e) {
            logger.error(ErrorEnum.RUNTIME_ERROR.getMsg(), e);
            return Result.error(ErrorEnum.RUNTIME_ERROR);
        }
        return Result.success(true);
    }

    public <E, D extends BaseDAO<E>> Result<E> save(E entity, D dao) {
        if (dao == null || entity == null) {
            return Result.error(ErrorEnum.PARAM_ERROR);
        }
        try {
            return Result.success(dao.save(entity));
        } catch (Exception e) {
            logger.error(ErrorEnum.RUNTIME_ERROR.getMsg(), e);
            return Result.error(ErrorEnum.RUNTIME_ERROR);
        }
    }

    public <E, D extends BaseDAO<E>> Result<E> saveWithFile(MultipartFile file, String dir, E entity, D dao) {
        if (dao == null || entity == null || file == null || file.isEmpty() || StringUtils.isBlank(dir)) {
            return Result.error(ErrorEnum.PARAM_ERROR);
        }
        try {
            FileUtils.save(file, dir);
            return Result.success(dao.save(entity));
        } catch (Exception e) {
            logger.error(ErrorEnum.RUNTIME_ERROR.getMsg(), e);
            return Result.error(ErrorEnum.RUNTIME_ERROR);
        }
    }
}
