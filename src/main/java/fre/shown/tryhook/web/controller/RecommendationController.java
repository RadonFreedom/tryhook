package fre.shown.tryhook.web.controller;

import fre.shown.tryhook.common.domain.Result;
import fre.shown.tryhook.core.book.domain.BookDetailVO;
import fre.shown.tryhook.core.book.domain.BookRecommendationVO;
import fre.shown.tryhook.core.book.BookService;
import fre.shown.tryhook.core.carousel.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Shaman
 * @date 2019/12/8 12:07
 */

@RestController
public class RecommendationController {

    @Autowired
    CarouselService carouselService;
    @Autowired
    BookService bookService;

    @RequestMapping("/carousel")
    public Result<String> getCarouselPath(Long id) {
        return carouselService.getCarouselPathById(id);
    }

    @RequestMapping("/recommend")
    public Result<List<BookRecommendationVO>> getBookRecommendations(Integer page, Integer size) {
        return bookService.bookRecommendationPageQuery(page, size);
    }

    @RequestMapping("/book")
    public Result<BookDetailVO> getBookDetail(Long id) {
        return bookService.getBookDetailById(id);
    }
}
