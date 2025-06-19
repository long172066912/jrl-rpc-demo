package rpc.client;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import rpc.anotation.RpcReference;

import java.lang.reflect.Field;

public class RpcReferenceBeanPostProcessor implements BeanPostProcessor {

    private final RpcClientProperties rpcClientProperties;

    public RpcReferenceBeanPostProcessor(RpcClientProperties rpcClientProperties) {
        this.rpcClientProperties = rpcClientProperties;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        //循环所有属性
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            //判断属性是否是RpcReference注解
            if (field.isAnnotationPresent(RpcReference.class)) {
                //赋值
                field.setAccessible(true);
                try {
                    Object fieldObj = field.get(bean);
                    if (fieldObj != null) {
                        return bean;
                    }
                    Object injectVal = RpcClientFactory.getClient(field.getType());
                    field.set(bean, injectVal);
                } catch (Exception e) {
                    throw new BeanCreationException(beanName, e.getMessage(), e);
                }
            }
        }

        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }
}
