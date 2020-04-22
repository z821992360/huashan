package com.imooc.o2o.dao;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.service.ShopCategoryService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ShopCategoryTest extends BaseTest {
    @Autowired
    private ShopCategoryDao shopCategoryDao;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Test
    public void testQueryShopCategory(){
        List<ShopCategory> shopCategories = shopCategoryDao.queryShopCategory(new ShopCategory());
        ShopCategory testCategory=new ShopCategory();
        ShopCategory parentCategory=new ShopCategory();
        parentCategory.setShopCategoryId(1L);
        testCategory.setParent(parentCategory);
        shopCategories = shopCategoryDao.queryShopCategory(testCategory);
        System.out.println(shopCategories.size());
    }
    @Test
    public void testQueryShopCategory2(){
        List<ShopCategory> shopCategories =shopCategoryService.getShopCategoryList(null);
        System.out.println(shopCategories.size());

    }
}
