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

    PARAM_ERROR("参数有误！"),
    RESULT_EMPTY("请求结果为空！"),
    ;

    private String msg;
}
