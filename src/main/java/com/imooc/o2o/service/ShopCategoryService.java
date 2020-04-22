package com.imooc.o2o.service;

import com.imooc.o2o.entity.ShopCategory;

import java.util.List;

public interface ShopCategoryService {
    /**
     * 根据查询获取Shopcategory列表
     * @param shopCategoryCondition
     * @return
     */
    public static  final  String SCLISTKEY ="shopcategorylist";
    List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
}
