package com.imooc.o2o;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *配置Spring和Junit整合，junit启动时加载SpringIoc容器
 */

@RunWith(SpringJUnit4ClassRunner.class)
//告诉Junit Spring配置文件的位置
@ContextConfiguration(locations = {"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml","classpath:spring/spring-redis.xml"})
public class BaseTest {

}
