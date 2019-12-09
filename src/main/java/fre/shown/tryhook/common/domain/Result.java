package fre.shown.tryhook.common.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Shaman
 * @date 2019/12/8 14:11
 */

@Getter
@ToString
@EqualsAndHashCode
public class Result<R> {

    /**
     * 接口执行成功失败标识
     */
    private boolean success;

    /**
     * 结果数据
     */
    private R value;

    /**
     * 错误或者提示信息
     */
    private String msg;

    private int code;

    private Result(Boolean success, R value, String msg, int code) {
        this.success = success;
        this.value = value;
        this.msg = msg;
        this.code = code;
    }

    public static <E> Result<E> success(E val) {
        return val == null ? error(ErrorEnum.RESULT_EMPTY)
                : new Result<>(true, val, "", -1);
    }

    public static <E> Result<E> error(String msg, int code) {
        return new Result<>(false, null, msg, code);
    }

    public static <E> Result<E> error(ErrorEnum errorEnum) {
        return new Result<>(false, null, errorEnum.getMsg(), errorEnum.getCode());
    }

    public static <E> boolean isSuccess(Result<E> result) {
        return result != null && result.success && result.value != null;
    }
}
