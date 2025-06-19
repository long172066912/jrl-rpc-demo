package rpc.client;

import rpc.config.ReferenceConfig;
import rpc.invoker.RpcInvoker;
import rpc.transport.RpcRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class RpcClientProxy implements InvocationHandler {

    private ReferenceConfig config;
    private Class<?> serviceInterface;
    private Object proxyInstance;


    public RpcClientProxy(Class<?> serviceInterface, ReferenceConfig config) {
        this.config = config;
        this.serviceInterface = serviceInterface;
        this.proxyInstance = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{this.serviceInterface}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        final Future<Object> future = RpcInvoker.invoke(RpcRequest.builder()
                .serviceName(serviceInterface.getName())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .parameters(args)
                .loadBalance("random")
                .cluster("failFast")
                .transport("http")
                .returnType(method.getReturnType())
                .build());
        return future.get(config.getTimeout(), TimeUnit.MILLISECONDS);
    }

    public Object getProxyInstance() {
        return proxyInstance;
    }
}
