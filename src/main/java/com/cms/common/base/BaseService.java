package com.cms.common.base;

/**
 * 共通サービス層の基底抽象クラス。
 * 基本的なCRUDメソッドを提供し、必要に応じてオーバーライド可能。
 * @param <T> エンティティの型
 */
public interface BaseService<T> {

//    /**
//     * IDでエンティティを検索
//     * @param id エンティティのID
//     * @return エンティティオブジェクト
//     */
//    public T findById(Long id) {
//        throw new UnsupportedOperationException("findById は未実装です");
//    }
//
//    /**
//     * すべてのエンティティを検索
//     * @return エンティティのリスト
//     */
//    public List<T> findAll() {
//        throw new UnsupportedOperationException("findAll は未実装です");
//    }
//
//    /**
//     * 条件に基づいてエンティティを検索
//     * @param conditions 検索条件（キー: フィールド名, 値: 条件値）
//     * @return 条件に一致するエンティティのリスト
//     */
//    public List<T> findByConditions(Map<String, Object> conditions) {
//        throw new UnsupportedOperationException("findByConditions は未実装です");
//    }
//
//    /**
//     * エンティティを保存
//     * @param entity エンティティオブジェクト
//     * @return 保存成功かどうか
//     */
//    public boolean save(T entity) {
//        throw new UnsupportedOperationException("save は未実装です");
//    }
//
//    /**
//     * エンティティを更新
//     * @param entity エンティティオブジェクト
//     * @return 更新成功かどうか
//     */
//    public boolean update(T entity) {
//        throw new UnsupportedOperationException("update は未実装です");
//    }
//
//    /**
//     * IDでエンティティを削除
//     * @param id エンティティのID
//     * @return 削除成功かどうか
//     */
//    public boolean deleteById(Long id) {
//        throw new UnsupportedOperationException("deleteById は未実装です");
//    }
//
//    /**
//     * 複数のIDを指定してエンティティを削除
//     * @param ids 削除対象のエンティティIDのリスト
//     * @return 削除成功かどうか
//     */
//    public boolean deleteByIds(List<Long> ids) {
//        throw new UnsupportedOperationException("deleteByIds は未実装です");
//    }
}
