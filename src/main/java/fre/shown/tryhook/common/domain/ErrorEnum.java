package fre.shown.tryhook.common.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Shaman
 * @date 2019/12/8 14:28
 */

@Getter
@AllArgsConstructor
public enum ErrorEnum {
    RESULT_EMPTY(0, "请求结果为空！"),
    PARAM_ERROR(1, "参数有误！"),
    ;

    private int code;
    private String msg;
}
