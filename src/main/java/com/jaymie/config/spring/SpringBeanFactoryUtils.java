package com.jaymie.config.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Author xq
 * @Description 创建SpringBeanFactoryUtils类
 * 创建SpringBeanFactoryUtils类
 * @Date 2018/12/20 11:51
 */
@Component
public class SpringBeanFactoryUtils implements ApplicationContextAware {
    private static ApplicationContext appCtx;

    /**
     * 重写setApplicationContext方法
     * 此方法可以把ApplicationContext对象inject到当前类中作为一个静态成员变量。
     *
     * @param applicationContext ApplicationContext 对象.
     * @throws BeansException
     * @author guanzheng
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        SpringBeanFactoryUtils.appCtx = applicationContext;
    }

    /**
     * 获取ApplicationContext
     *
     * @return
     * @author guanzheng
     */
    public static ApplicationContext getApplicationContext() {
        return appCtx;
    }

    /**
     * 这是一个便利的方法，帮助我们快速得到一个BEAN
     *
     * @param beanName bean的名字
     * @return 返回一个bean对象
     * @author guanzheng
     */
    public static Object getBean(String beanName) {
        return appCtx.getBean(beanName);
    }
}
