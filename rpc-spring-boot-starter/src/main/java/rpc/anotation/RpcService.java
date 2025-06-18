package rpc.anotation;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Service
public @interface RpcService {

}
