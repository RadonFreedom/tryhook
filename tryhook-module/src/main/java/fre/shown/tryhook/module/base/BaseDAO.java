package fre.shown.tryhook.module.base;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Shaman
 * @date 2019/12/6 13:57
 */

public interface BaseDAO<E, Q extends BaseQuery> {

    /**
     * 插入一条记录
     *
     * @param e 原对象信息
     * @return generateKeys
     */
    Long insert(E e) throws SQLException;

    /**
     * 根据自增主键ID索引一条记录数据
     *
     * @param id 记录ID
     * @return 当前ID对应数据详情
     */
    E select(Long id) throws SQLException;

    /**
     * 更新记录
     *
     * @param e 原对象信息
     * @return 更新执行结果
     */
    Boolean update(E e) throws SQLException;

    /**
     * 根据id删除记录
     */
    Boolean delete(Long id) throws SQLException;

    /**
     * 根据查询项查询记录
     *
     * @param q 查询条件
     * @return 查询结果
     */
    List<E> query(Q q) throws SQLException;

    /**
     * 根据查询项统计记录条数
     *
     * @param q 查询条件
     * @return 记录总数
     */
    Integer count(Q q) throws SQLException;
}
