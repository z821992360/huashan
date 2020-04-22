package com.imooc.service;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.enums.ShopStateEnum;
import com.imooc.o2o.exceptions.ShopOperationException;
import com.imooc.o2o.service.ShopService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

public class ShopServiceTest extends BaseTest {
    @Autowired
    private ShopService shopService;
    @Test
    public void testAddShop() throws FileNotFoundException {
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
        shop.setShopName("测试店铺21");
        shop.setShopDesc("test1");
        shop.setShopAddr("test1");
        shop.setPhone("test1");

        shop.setCreateTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("审核中");
        File shopImg=new File("/image/xiaohuangren.jpg");
        InputStream is=new FileInputStream(shopImg);
        ShopExecution shopExecution = shopService.addShop(shop,new ImageHolder(shopImg.getName(),is));
        System.out.println(shopExecution.getState());

    }
    @Test
    public  void testModifyShop() throws ShopOperationException,FileNotFoundException {
        Shop shop=new Shop();
        shop.setShopId(1L);
        shop.setShopName("修改后的店铺名称");
        File shopImg=new File("/image/xiaohuangren.jpg");
        InputStream is=new FileInputStream(shopImg);
        ShopExecution shopExecution = shopService.addShop(shop,new ImageHolder("xiaohuangren.jpg",is));
        System.out.println(shopExecution.getShop().getShopImg());
        System.out.println(shopExecution.getShop().getShopName());

    }
    @Test
    public void testGetShopList(){
        Shop shopCondition=new Shop();
        ShopCategory sc=new ShopCategory();
        sc.setShopCategoryId(3L);
        shopCondition.setShopCategory(sc);
        ShopExecution shopList = shopService.getShopList(shopCondition, 3, 2);
        System.out.println("店铺列表数"+shopList.getShopList().size());
        System.out.println("店铺列表总数"+shopList.getCount());
    }
}
