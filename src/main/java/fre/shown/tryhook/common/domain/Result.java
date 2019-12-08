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
    private Boolean success;

    /**
     * 结果数据
     */
    private R value;

    /**
     * 错误或者提示信息
     */
    private String msg;

    private Result(Boolean success, R value, String msg) {
        this.success = success;
        this.value = value;
        this.msg = msg;
    }

    public static <E> Result<E> success(E val) {
        return val == null ? error(ErrorEnum.RESULT_EMPTY)
                : new Result<>(Boolean.TRUE, val, "");
    }

    public static <E> Result<E> error(String msg) {
        return new Result<>(Boolean.FALSE, null, msg);
    }

    public static <E> Result<E> error(ErrorEnum errorEnum) {
        return new Result<>(Boolean.FALSE, null, errorEnum.getMsg());
    }

    public static <E> boolean isSuccess(Result<E> result) {
        return result != null && result.success != null && result.success.equals(true) && result.value != null;
    }
}
