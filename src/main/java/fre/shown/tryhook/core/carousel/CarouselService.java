package fre.shown.tryhook.core.carousel;

import fre.shown.tryhook.common.constant.PathConstant;
import fre.shown.tryhook.common.domain.ErrorEnum;
import fre.shown.tryhook.common.domain.Result;
import fre.shown.tryhook.common.util.DataUtils;
import fre.shown.tryhook.common.util.FileUtils;
import fre.shown.tryhook.module.base.ManagerHelper;
import fre.shown.tryhook.module.carousel.dao.CarouselDAO;
import fre.shown.tryhook.module.carousel.domain.CarouselDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * @author Shaman
 * @date 2019/12/8 13:52
 */

@Service
public class CarouselService {

    @Autowired
    CarouselDAO carouselDAO;
    @Autowired
    ManagerHelper managerHelper;
    Logger logger = LoggerFactory.getLogger(CarouselService.class);

    public Result<String> getCarouselPathById(Long id) {

        if (DataUtils.isIllegal(id)) {
            return Result.error(ErrorEnum.PARAM_ERROR);
        }

        Optional<CarouselDO> carouselDO = carouselDAO.findById(id);
        if (carouselDO.isPresent()) {
            String path = carouselDO.get().getChartPath();
            if (path != null) {
                return Result.success(path);
            }
        }

        logger.error(ErrorEnum.RESULT_EMPTY.getMsg() + "carousel id: " + id);
        return Result.error(ErrorEnum.RESULT_EMPTY);
    }

    public Result<List<CarouselDO>> getCarousel(Integer page, Integer size) {
        return managerHelper.pageQuery(page, size, carouselDAO);
    }

    public Result<Boolean> saveCarousel(MultipartFile chart, Long id) {
        CarouselDO carouselDO = new CarouselDO();
        carouselDO.setId(id);
        String dir = PathConstant.CAROUSEL_PATH_PREFIX + FileUtils.getRandomFileName(chart);
        carouselDO.setChartPath(dir);
        Result<CarouselDO> result = managerHelper.saveWithFile(chart, dir, carouselDO, carouselDAO);
        return Result.isSuccess(result) ? Result.success(true) : Result.error(result);
    }

    public Result<Boolean> deleteCarouselByIds(List<Long> ids) {
        return managerHelper.deleteAllById(ids, carouselDAO);
    }
}
