package rpc.client;

import rpc.config.ReferenceConfig;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

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
        return method.invoke(proxyInstance, args);
    }

    public Object getProxyInstance() {
        return proxyInstance;
    }
}
