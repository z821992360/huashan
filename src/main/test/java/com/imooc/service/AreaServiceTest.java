package com.imooc.service;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.service.AreaService;
import com.imooc.o2o.service.CacheService;
import com.imooc.o2o.service.impl.AreaServiceImpl;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class AreaServiceTest extends BaseTest {
    @Autowired
    private AreaService areaService;
    @Autowired
    private CacheService cacheService;
    @Test
    public void testGetAreaList(){
         List<Area> areaList=areaService.getAreaList();

        cacheService.removeFromCache(areaService.AREALISTKEY);
        areaList=areaService.getAreaList();
    }
    @Test
    public void test() throws IOException {
      String path=  Thread.currentThread().getContextClassLoader().getResource("").getPath();
        System.out.println(path.substring(1));
        System.out.println(path.substring(1)+"watermark.jpg");
     Thumbnails.of(new File("D:\\image\\xiaohuangren.jpg")).size(200,200)
             .watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(path.substring(1)+"watermark.jpg")),0.25f)
             .outputQuality(0.8f).toFile("D:\\image\\xiaohuangren1.jpg");
    }
}
