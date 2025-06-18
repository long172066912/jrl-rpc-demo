package rpc.anotation;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Service
public @interface ZeusService {
    public String interfaceName() default "";

    public String serverName()  default "";

    public int processTimeout() default 2000;

    public int coreThreadSize() default 20;

    public int maxThreadSize() default 300;

    /**
     * 线程保活时间，默认30秒
     * @return
     */
    public int keepAliveTime() default 30;

    /**
     * 预先创建核心线程
     * @return
     */
    public boolean prestartCoreThread() default false;
}
