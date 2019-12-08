package fre.shown.tryhook.core.carousel;

import fre.shown.tryhook.common.domain.ErrorEnum;
import fre.shown.tryhook.common.domain.Result;
import fre.shown.tryhook.module.carousel.dao.CarouselDAO;
import fre.shown.tryhook.module.carousel.domain.CarouselDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Shaman
 * @date 2019/12/8 13:52
 */

@Service
public class CarouselService {

    @Autowired
    CarouselDAO carouselDAO;
    Logger logger = LoggerFactory.getLogger(CarouselService.class);

    public Result<String> getCarouselPathById(Long id) {

        if (id == null) {
            logger.error(ErrorEnum.PARAM_ERROR.getMsg());
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
}
