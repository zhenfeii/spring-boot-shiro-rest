package com.fengche.rest.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 *  Eean 工具类
 * Created by Administrator on  2018/9/21
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

   private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.applicationContext = context;
    }

    /**
     * 获取bean
     * @param name
     * @return
     */
    public static Object getBean(String name){
        return applicationContext.getBean(name);

    }

    public static <T> T getBean(Class<T> aClass){
        return applicationContext.getBean(aClass);
    }

    /**
     * 获取所有的bean
     * @return
     */
    public static String[] getBeans(){
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        return beanDefinitionNames;
    }
}
