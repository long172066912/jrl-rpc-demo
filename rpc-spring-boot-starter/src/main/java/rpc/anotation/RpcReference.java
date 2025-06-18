package rpc.anotation;


import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface RpcReference {
    /**
     * 超时时间
     *
     * @return
     */
    public int timeout() default 2000;
}
