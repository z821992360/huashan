package com.imooc.o2o.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.cache.JedisUtil;
import com.imooc.o2o.dao.AreaDao;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.exceptions.AreaOperationException;
import com.imooc.o2o.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Service
public class AreaServiceImpl implements AreaService {
     @Autowired
     private AreaDao areaDao;
     @Autowired
     private JedisUtil.Keys jedisKeys;
     @Autowired
     private  JedisUtil.Strings jedisStrings;
     private static Logger logger=LoggerFactory.getLogger(AreaServiceImpl.class);

    @Override
    public List<Area> getAreaList() {
        String key=AREALISTKEY;
        List<Area> areaList=null;
        ObjectMapper mapper=new ObjectMapper();
        if(!jedisKeys.exists(key)){
            areaList=areaDao.queryArea();
            try {
                String jsonString=mapper.writeValueAsString(areaList);
                jedisStrings.set(key,jsonString);
            } catch (JsonProcessingException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
                throw new AreaOperationException(e.getMessage());
            }
        }else {
            String jsonString=jedisStrings.get(key);
            JavaType javaType=mapper.getTypeFactory().constructParametricType(ArrayList.class,Area.class);
            try {
                areaList=mapper.readValue(jsonString,javaType);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                e.printStackTrace();
                throw new AreaOperationException(e.getMessage());
            }
        }
        return  areaList;
    }
}
