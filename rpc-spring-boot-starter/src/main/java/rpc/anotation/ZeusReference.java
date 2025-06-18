package rpc.anotation;


import com.wb.zeus.common.utils.Constants;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface ZeusReference {

    public String appKey() default "";

    public String interfaceName() default "";

    public String serverName() default "";

    public int connTimeout() default 1500;

    public int timeout() default 2000;

    public int failoverRetryTimes() default 0;

    String[] methodFailoverRetryTimes() default "";

    public String callWay() default "sync";

    public String protocol() default "wb";

    public String serialize() default "hessian";

    public String clusterType() default "failfast";

    public String loadbalance() default "weight";

    public String router() default "none";

    public String registry() default "";

    public boolean onProxy() default false;

    public boolean inJvm() default false;

    public String localImplClazz() default "";

    String[] methodTimeout() default "";

    /**
     * 是否需要预连接
     */
    public boolean lazy() default true;

    public String group() default "";

    public String version() default "";

    /**
     * 限流后抛异常，不执行Fallback
     * @return
     */
    public boolean limitThrow() default Constants.LIMIT_THROW;
}
