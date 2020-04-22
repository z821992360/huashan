package com.imooc.o2o.dao;

import com.imooc.o2o.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCategoryDao {
    /**
     * 通过shopId查询店铺商品分类
     * @param shopId
     * @return
     */
    List<ProductCategory> queryProductCategoryList(Long shopId);
    /**
     * 批量添加商品类别
     * @param productCategoryList
     * @return
     */
    int batchInsertProductCategory(List<ProductCategory> productCategoryList);

    /**
     * 删除指定商品类别
     * @param productId
     * @param shopId
     * @return
     */
    int deletProductCategory(@Param("productCategoryId" ) long productId,@Param("shopId")long shopId);
}
