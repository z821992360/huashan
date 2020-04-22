package com.imooc.o2o.web.frontend;

import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.service.AreaService;
import com.imooc.o2o.service.ShopCategoryService;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/frontend")
public class ShopListController {
    @Autowired
    private AreaService areaService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private ShopService shopService;

    /**
     * 返回商品列表里的ShopCategory列表(二级或者一级),以及区域信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/listshopspageinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> listShopPageInfo(HttpServletRequest request){
        Map<String,Object> modelMap=new HashMap<>();
        //试着从前台获取请求中的parentId
        Long parentId= HttpServletRequestUtil.getLong(request,"parentId");
        List<ShopCategory> shopCategoryList=null;
        //如果ParentId存在，则取出一级ShopCategory下的二级ShopCategory
        if(parentId!=-1){
            try {
                ShopCategory shopCategoryCondition=new ShopCategory();
                ShopCategory parent=new ShopCategory();
                parent.setShopCategoryId(parentId);
                shopCategoryCondition.setParent(parent);
                shopCategoryList=shopCategoryService.getShopCategoryList(shopCategoryCondition);
            }catch (Exception e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
            }
        }else {
            try{
                //如果paretnId不存在,则取出所有一级ShopCategory(用户在首页选择的是全部商店类别)
                shopCategoryList=shopCategoryService.getShopCategoryList(null);
            }catch (Exception e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
            }
        }
        modelMap.put("shopCategoryList",shopCategoryList);
        List<Area> areaList=null;
        try {
            //获取区域信息
            areaList=areaService.getAreaList();
            modelMap.put("success",true);
            modelMap.put("areaList",areaList);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
        }
        return modelMap;
    }
    /**
     * 获取指定条件下的店铺列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/listshops",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> listShops(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //获取页码
        int pageIndex=HttpServletRequestUtil.getInt(request,"pageIndex");
        //获取一页所需要的条目数
        int pageSize=HttpServletRequestUtil.getInt(request,"pageSize");
        //非空判断
        if(pageIndex>-1&&pageSize>-1){
            //试着获取以及类别Id
            long parentId=HttpServletRequestUtil.getLong(request,"parentId");
            //试着获取特定二级类别Id
            long shopCategoryId=HttpServletRequestUtil.getLong(request,"shopCategoryId");
            //试着获取区域Id
            int areaId=HttpServletRequestUtil.getInt(request,"areaId");
            //试着获取模查询的名字
            String shopName=HttpServletRequestUtil.getString(request,"shopName");
            //试着组合之后的查询条件
            Shop shopCondition=compatchShopCondition4Search(parentId,shopCategoryId,areaId,shopName);
            //根据查询条件和分页信息获取店铺列表,并返回总数
            ShopExecution se=shopService.getShopList(shopCondition,pageIndex,pageSize);
            modelMap.put("shopList",se.getShopList());
            modelMap.put("count",se.getCount());
            modelMap.put("success",true);
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","empty pageSize or pageIndex");
        }
        return modelMap;
    }

    /**
     * 结合查询条件,并将条件封装到ShopCondition对象返回
     * @param parentId
     * @param shopCategoryId
     * @param areaId
     * @param shopName
     * @return
     */

    private Shop compatchShopCondition4Search(long parentId, long shopCategoryId, int areaId, String shopName) {
        Shop shopCondition=new Shop();
        if(parentId!=-1L) {
            //查询某个一级ShopCategory下面的所有二级ShopCategory里面的店铺列表
            ShopCategory childCategory=new ShopCategory();
            ShopCategory parentCategory=new ShopCategory();
            parentCategory.setShopCategoryId(parentId);
            childCategory.setParent(parentCategory);
            shopCondition.setShopCategory(childCategory);
        }
        if(shopCategoryId!=-1){
            //查询某个二级ShopCategory下面的店铺列表
            ShopCategory shopCategory=new ShopCategory();
            shopCategory.setShopCategoryId(shopCategoryId);
            shopCondition.setShopCategory(shopCategory);
        }
        if(areaId!=-1){
            //查询位于某个区域Id下的店铺
            Area area=new Area();
            area.setAreaId(areaId);
            shopCondition.setArea(area);
        }
        if(shopName!=null){
            //查询名字里保护shopName的店铺
            shopCondition.setShopName(shopName);
        }
        //前端展示的页码的哦是审核成功页面
        shopCondition.setEnableStatus(1);
        return shopCondition;
    }

}
