package rpc.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import rpc.anotation.RpcService;
import rpc.registry.local.RpcLocalRegistry;

import java.util.Map;

/**
 * @author JerryLong
 * @version V1.0
 * @Title: ZeusServerApplicationListener
 * @Description: //TODO (用一句话描述该文件做什么)
 * @date 2023/3/22 10:37
 */
@Component
@EnableConfigurationProperties(RpcServerProperties.class)
public class RpcServerApplicationListener implements ApplicationListener<ContextRefreshedEvent>, ApplicationContextAware {

    private static Logger LOGGER = LoggerFactory.getLogger(RpcServerApplicationListener.class);

    @Autowired
    private RpcServerProperties rpcServerProperties;

    private ApplicationContext applicationContext;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //查找所有@RpcService bean
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(RpcService.class);
        if (beans.size() > 0) {
            for (Map.Entry<String, Object> entry : beans.entrySet()) {
                //找到该bean继承的client
                final Class<?> client = getTopLevelInterface(entry.getValue().getClass());
                //注册
                if (null != client) {
                    final RpcService rpcService = client.getAnnotation(RpcService.class);
                    final String app = rpcServerProperties.getApp();
                    final int port = rpcServerProperties.getPort();
                    //todo
                }
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private static Class<?> getTopLevelInterface(Class<?> clazz) {
        Class<?>[] interfaces = clazz.getInterfaces();

        // 如果没有接口，返回 null
        if (interfaces.length == 0) {
            // 递归检查父类
            Class<?> superclass = clazz.getSuperclass();
            if (superclass != null) {
                return getTopLevelInterface(superclass);
            }
            return null;
        }

        // 返回第一个接口，如果有多个接口可以根据业务需求选择
        return interfaces[0];
    }
}