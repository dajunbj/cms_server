package com.cms.common.base;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 共通Mapperインターフェース。
 * MyBatisの基本的なCRUD操作を提供。
 * @param <T> エンティティの型
 */
public interface BaseMapper<T> {

    /**
     * IDでエンティティを検索
     * @param id エンティティのID
     * @return エンティティオブジェクト
     */
    T selectById(@Param("id") Long id);

    /**
     * すべてのエンティティを検索
     * @return エンティティのリスト
     */
    List<T> selectAll();

    /**
     * 条件に基づいてエンティティを検索
     * @param conditions 検索条件（キー: フィールド名, 値: 条件値）
     * @return 条件に一致するエンティティのリスト
     */
    List<T> selectByConditions(@Param("conditions") Map<String, Object> conditions);

    /**
     * エンティティを挿入
     * @param entity 挿入するエンティティオブジェクト
     * @return 挿入成功した行数
     */
    int insert(T entity);

    /**
     * エンティティを更新
     * @param entity 更新するエンティティオブジェクト
     * @return 更新成功した行数
     */
    int update(T entity);

    /**
     * IDでエンティティを削除
     * @param id 削除対象のエンティティID
     * @return 削除成功した行数
     */
    int deleteById(@Param("id") Long id);

    /**
     * 複数のIDを指定してエンティティを削除
     * @param ids 削除対象のエンティティIDのリスト
     * @return 削除成功した行数
     */
    int deleteByIds(@Param("ids") List<Long> ids);
}