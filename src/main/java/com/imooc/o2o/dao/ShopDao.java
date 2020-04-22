package com.imooc.o2o.dao;

import com.imooc.o2o.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopDao {
    /**
     * 返回queryShopList总数
     * @param shopCondition
     * @return
     */
    int queryShopCount(@Param("shopCondition")Shop shopCondition);
    /**
     *  分页查询，可查询的条件有:店铺名(模糊),店铺状态,店铺类别,区域Id,owner
     * @param shopCondition 查询的条件
     * @param rowIndex 从第几行开始取
     * @param pageSize 返回的条数
     * @return
     */
    List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition,@Param("rowIndex" ) int rowIndex,@Param("pageSize")int pageSize);
    /**
     * 新增店铺
     * @param shop
     * @return
     */
    int insertShop(Shop shop);
    /**
     * 更新店铺信息
     */
    int updateShop(Shop shop);

    /**
     * 查询店铺信息
     * @param shopId
     * @return
     */
    Shop queryByShopId(Long shopId);

}
