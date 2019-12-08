package fre.shown.tryhook.module.book.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Shaman
 * @date 2019/12/2 15:43
 */

@Getter
@AllArgsConstructor
public enum BookStatusEnum {

    AVAILABLE(1, "上架"),
    UNAVAILABLE(0, "下架"),
    ;

    private final int id;
    private final String desc;
}
