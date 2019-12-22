package fre.shown.tryhook.web;

import fre.shown.tryhook.common.domain.Result;
import fre.shown.tryhook.core.book.BookService;
import fre.shown.tryhook.core.book.domain.BookDetailVO;
import fre.shown.tryhook.core.book.domain.BookRecommendationVO;
import fre.shown.tryhook.core.book.domain.BookSearchVO;
import fre.shown.tryhook.module.book.domain.BookCategoryDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

/**
 * @author Shaman
 * @date 2019/12/8 16:31
 */

@RestController
public class BookController {

    @Autowired
    BookService bookService;

    @RequestMapping("/search")
    public Result<List<BookSearchVO>> searchBooks(String keyword) {
        return bookService.searchBooks(keyword);
    }

    @RequestMapping("/category")
    public Result<List<BookCategoryDO>> getBookCategories() {
        return bookService.getBookCategories();
    }

    @RequestMapping("/books")
    public Result<List<BookRecommendationVO>> getBooksByCategory(Long categoryId) {
        return bookService.getBooksByCategoryId(categoryId);
    }

    @RequestMapping("/book/{id}")
    public Result<BookDetailVO> getBookDetail(@PathVariable Long id, Principal principal) {
        return bookService.getBookDetailById(id, principal.getName());
    }
}
