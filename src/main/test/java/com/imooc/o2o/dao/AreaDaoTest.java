package com.imooc.o2o.dao;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AreaDaoTest extends BaseTest {
    @Autowired
    private AreaDao areaDao;
    @Test
    public void testQueryArea(){
        List<Area> areaList = areaDao.queryArea();
        for (Area area:areaList){
            System.out.println(area.getAreaId()+":"+area.getAreaName());
        }
    }
    @Test
    public void test(){
         String seperator=System.getProperty("file.separator");

        System.out.println(seperator);
        String basePath=Thread.currentThread().getContextClassLoader().getResource("").getPath();
        System.out.println(basePath);
    }
}
