package fre.shown.tryhook.web;

import fre.shown.tryhook.common.domain.Result;
import fre.shown.tryhook.core.book.BookService;
import fre.shown.tryhook.core.carousel.CarouselService;
import fre.shown.tryhook.core.user.PrincipalVO;
import fre.shown.tryhook.core.user.UserService;
import fre.shown.tryhook.core.user.UserVO;
import fre.shown.tryhook.module.book.domain.BookCategoryDO;
import fre.shown.tryhook.module.book.domain.BookDO;
import fre.shown.tryhook.module.book.domain.BookVideoDO;
import fre.shown.tryhook.module.carousel.domain.CarouselDO;
import fre.shown.tryhook.module.user.domain.PrincipalCfgDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Shaman
 * @date 2019/12/7 12:49
 */

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    CarouselService carouselService;
    @Autowired
    BookService bookService;
    @Autowired
    UserService userService;

    @GetMapping("/carousel")
    public Result<List<CarouselDO>> getCarousel(Integer page, Integer size) {
        return carouselService.getCarousel(page, size);
    }

    @PostMapping(value = "/carousel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<Boolean> saveCarousel(MultipartFile chart, Long id) {
        return carouselService.saveCarousel(chart, id);
    }

    @DeleteMapping("/carousel")
    public Result<Boolean> deleteCarousel(List<Long> ids) {
        return carouselService.deleteCarouselByIds(ids);
    }

    @GetMapping("/book")
    public Result<List<BookDO>> getBook(Integer page, Integer size) {
        return bookService.getBook(page, size);
    }

    @PostMapping(value = "/book", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<Boolean> saveBook(MultipartFile cover, BookDO book) {
        return bookService.saveBook(cover, book);
    }

    @DeleteMapping("/book")
    public Result<Boolean> deleteBook(List<Long> ids) {
        return bookService.deleteBookByIds(ids);
    }

    @GetMapping("/bookCategory")
    public Result<List<BookCategoryDO>> getBookCategory(Integer page, Integer size) {
        return bookService.getBookCategory(page, size);
    }

    @PostMapping(value = "/bookCategory")
    public Result<Boolean> saveBookCategory(BookCategoryDO book) {
        return bookService.saveBookCategory(book);
    }

    @DeleteMapping("/bookCategory")
    public Result<Boolean> deleteBookCategory(List<Long> ids) {
        return bookService.deleteBookCategoryByIds(ids);
    }

    @GetMapping("/bookVideo")
    public Result<List<BookVideoDO>> getBookVideo(Integer page, Integer size, Long bookId) {
        return bookService.getBookVideo(page, size, bookId);
    }

    @PostMapping(value = "/bookVideo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<Boolean> saveBookVideo(MultipartFile video, BookVideoDO bookVideo) {
        return bookService.saveBookVideo(video, bookVideo);
    }

    @DeleteMapping("/bookVideo")
    public Result<Boolean> deleteBookVideo(List<Long> ids) {
        return bookService.deleteBookVideoByIds(ids);
    }

    @GetMapping("/user")
    public Result<List<UserVO>> getUser(Integer page, Integer size) {
        return userService.getUser(page, size);
    }

    @DeleteMapping("/user")
    public Result<Boolean> deleteUser(List<Long> ids) {
        return userService.deleteUserByIds(ids);
    }

    @GetMapping("/principal")
    public Result<List<PrincipalVO>> getPrincipal(Integer page, Integer size) {
        return userService.getPrincipal(page, size);
    }

    @PostMapping(value = "/principal")
    public Result<Boolean> savePrincipal(PrincipalCfgDO principalCfgDO) {
        return userService.savePrincipal(principalCfgDO);
    }

    @DeleteMapping("/principal")
    public Result<Boolean> deletePrincipal(List<Long> ids) {
        return userService.deletePrincipalByIds(ids);
    }

}
