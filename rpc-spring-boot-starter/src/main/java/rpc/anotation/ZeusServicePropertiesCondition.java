package rpc.anotation;

import java.lang.annotation.*;

/**
 * @author JerryLong
 * @version V1.0
 * @Title: ZeusServiceCondition
 * @Description:
 * @date 2023/10/27 16:52
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface ZeusServicePropertiesCondition {
    /**
     * Properties参数条件如： "appname=a"
     */
    String[] value() default "";

    /**
     * 间隔符，默认是 =
     *
     * @return
     */
    String interval() default "=";
}
