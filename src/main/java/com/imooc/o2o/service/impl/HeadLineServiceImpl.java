package com.imooc.o2o.service.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.cache.JedisUtil;
import com.imooc.o2o.dao.HeadLineDao;
import com.imooc.o2o.entity.HeadLine;
import com.imooc.o2o.exceptions.HeadLineOperationException;
import com.imooc.o2o.service.HeadLineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Service
public class HeadLineServiceImpl implements HeadLineService {
    @Autowired
    private HeadLineDao headLineDao;
    @Autowired
    private JedisUtil.Keys jedisKeys;
    @Autowired
    private JedisUtil.Strings jedisStrings;

    private static Logger logger= LoggerFactory.getLogger(HeadLineServiceImpl.class);
    @Override
    @Transactional
    public List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException {
        //定义redis的key的前缀
        String key=HLLLISTKEY;
        //定义接收对象
        List<HeadLine> headLineList=null;
        //定义jackson数据转换操作类
        ObjectMapper objectMapper=new ObjectMapper();
        //拼接出redis的key
        if(headLineCondition!=null&&headLineCondition.getEnableStatus()!=null){
            key=key+"_"+headLineCondition.getEnableStatus();
        }
        //判断key是否存在
        if(!jedisKeys.exists(key)){
            //若不存在,则直接冲数据库里面取出相应数据
            headLineList=headLineDao.queryHeadLine(headLineCondition);
            //将相关的实体类集合转换成String,存放如redis里面的对应key中
            String jsonString;
            try{
               jsonString=objectMapper.writeValueAsString(headLineList);
            }catch (JsonProcessingException e){
                e.printStackTrace();;
                logger.error(e.getMessage());
                throw new HeadLineOperationException(e.getMessage());
            }
        }else {
            //若存在，则直接从redis中取出相应的数据
            String jsonString=jedisStrings.get(key);
            //指定要将String转成成集合类型
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, HeadLine.class);
            try{
                //将相关的key对应的value里的string转换成对象实体类的集合
                headLineList=objectMapper.readValue(jsonString,javaType);
            }catch (JsonParseException e){
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new HeadLineOperationException(e.getMessage());
            }

        }
        return headLineList ;
    }
}
