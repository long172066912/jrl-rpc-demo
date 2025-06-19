package rpc.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import rpc.transport.RpcRequest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RestController
public class RpcController {

    @PostMapping("/rpc")
    public Object rpc(@RequestBody RpcRequest request) {
        final Object provider = RpcServerApplicationListener.PROVIDERS.get(request.getServiceName());
        try {
            final Method method = provider.getClass().getMethod(request.getMethodName(), request.getParameterTypes());
            return method.invoke(provider, request.getParameters());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
