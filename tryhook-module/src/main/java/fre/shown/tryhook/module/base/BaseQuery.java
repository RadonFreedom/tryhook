package fre.shown.tryhook.module.base;

import lombok.Data;

/**
 * @author Shaman
 * @date 2019/12/6 14:01
 */

@Data
public class BaseQuery {
    private static final Integer DEFAULT_PAGE_SIZE = 20;
    /**
     * 当前页码，从1开始
     */
    private Integer curPage;
    /**
     * 每页大小
     */
    private Integer pageSize;

    public BaseQuery() {
        this.curPage = 1;
        this.pageSize = DEFAULT_PAGE_SIZE;
    }

    /**
     * limit子句第一个参数
     */
    public Integer getStart() {
        return (this.getCurPage() - 1) * pageSize;
    }
}
