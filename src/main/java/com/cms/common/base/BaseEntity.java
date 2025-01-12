package com.cms.common.base;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 共通エンティティクラス。共通フィールドをカプセル化。
 */
@Data
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * エンティティID
     */
    private Long id;

    /**
     * 作成日時
     */
    private Date createdAt;

    /**
     * 更新日時
     */
    private Date updatedAt;

}
