package com.cms.module.customer.mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cms.module.customer.entity.CustomerBean;
import com.cms.module.customer.entity.ResponsibleBean;
import com.cms.module.customer.form.CustomerForm;
import com.cms.module.customer.form.ResponsibleForm;

/**
 * ユーザー情報 Mapper
 */
@Mapper
public interface CustomerMapper {
	
	/**
	 * 顾客レコード件数取得
	 * 
	 * @param conditions 条件
	 * @return レコード件数
	 */
	int selectCustomerCount(Map<String, Object> conditions);
	
	/**
	 * 责任者レコード件数取得
	 * 
	 * @param conditions 条件
	 * @return レコード件数
	 */
	int selectResponsibleCount(Map<String, Object> conditions);

	/**
	 * 顾客情報検索
	 * 
	 * @param conditions 条件
	 * @return 顾客情報
	 */
	List<CustomerBean> selectCustomer(Map<String, Object> map);
	
	/**
	 * 责任者情報検索
	 * 
	 * @param conditions 条件
	 * @return 责任者情報
	 */
	List<ResponsibleBean> selectResponsible(Map<String, Object> map);
	
    /**
     * 根据顾客ID查找初始化信息
     * 
     * @param 顾客ID
     * @return 顾客情报
     */
	CustomerBean findByCustomerId (@Param("customer_id") String customer_id);
	
	 /**
     * 根据责任者ID查找初始化信息
     * 
     * @param 责任者ID
     * @return 责任者情报
     */
	ResponsibleBean findByResponsibleId (@Param("responsible_id") String responsible_id);
	
    /**
     *   检索顾客更新日期进行乐观check
     *   @param id
     *   @return 更新日期
     */
     LocalDateTime getCustomerUpdate(int id);
     
     /**
      *   检索责任者更新日期进行乐观check
      *   @param id
      *   @return 更新日期
      */
      LocalDateTime getResponsibleUpdate(int id);
      
	 /**
	  * 删除选中顾客
	  * 
	  * @param 选中条目的契约ID
	  * @return 行为结果
	  */
	  public void deleteSelectedCustomer(Map<String, Object> conditions);
	  
	  /**
	  * 删除选中责任者
	  * 
	  * @param 选中条目的契约ID
	  * @return 行为结果
	  */
	  public void deleteSelectedResponsible(Map<String, Object> conditions);
	
	  /**
      * 插入员工信息
      * 
      * @param employee 员工实体
      * @return 影响的行数
	  */
	  int registerCustomer(CustomerForm input);
	  
	  /**
	  * 插入责任者信息
	  * 
	  * @param employee 员工实体
	  * @return 影响的行数
      */
      int registerResponsible(ResponsibleForm input);
      
      /**
       * 更新顾客信息
       * 
       * @param input
       * @return 影响的行数
       */
      int updateCustomer(CustomerBean input);
      
      /**
       * 更新责任信息
       * 
       * @param input
       * @return 影响的行数
       */
      int updateResponsible(ResponsibleBean input);
}









