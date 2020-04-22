package com.imooc.o2o.dao;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class ShopDaoTest extends BaseTest {
    @Autowired
    private ShopDao shopDao;

    @Test
    public void testInsertShop(){
        Shop shop=new Shop();
        PersonInfo owner=new PersonInfo();
        Area area=new Area();
        ShopCategory shopCategory=new ShopCategory();
        owner.setUserId(1L);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试店铺");
        shop.setShopDesc("test");
        shop.setShopAddr("test");
        shop.setPhone("test");
        shop.setShopImg("test");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(1);
        shop.setAdvice("审核中");
        int i = shopDao.insertShop(shop);
        System.out.println(i);
    }
    @Test
    public void testUpdateShop(){
        Shop shop=new Shop();
        shop.setShopId(1L);
        shop.setShopDesc("测试描述");
        shop.setShopAddr("测试地址");
        shop.setLastEditTime(new Date());
        int i = shopDao.updateShop(shop);
        System.out.println(i);
    }
    @Test
    public void testQueryByShopId(){
        long shopId=1L;
        Shop shop=shopDao.queryByShopId(shopId);
        System.out.println("area_id:"+shop.getArea().getAreaId());
        System.out.println("area_name:"+shop.getArea().getAreaName());
    }
    @Test
    public void testQueryShopList(){
        Shop shopCondition=new Shop();
        ShopCategory chilCategory=new ShopCategory();
        ShopCategory parentCategory=new ShopCategory();
        parentCategory.setShopCategoryId(12L);
        chilCategory.setParent(parentCategory);
        shopCondition.setShopCategory(chilCategory);

        List<Shop> shopList = shopDao.queryShopList(shopCondition, 0, 10);
        int i = shopDao.queryShopCount(shopCondition);
        System.out.println("店铺列表的大小"+shopList.size());
        System.out.println("店铺总数"+i);
    }
    @Test
    public  void test(){

    }
}
