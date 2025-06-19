package rpc.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc.config.ReferenceConfig;
import rpc.invoker.RpcInvoker;
import rpc.transport.RpcRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class RpcClientProxy implements InvocationHandler {
    private Logger LOGGER = LoggerFactory.getLogger(RpcClientProxy.class);
    private ReferenceConfig config;
    private Class<?> serviceInterface;
    private Object proxyInstance;
    private AtomicLong id = new AtomicLong(0);


    public RpcClientProxy(Class<?> serviceInterface, ReferenceConfig config) {
        this.config = config;
        this.serviceInterface = serviceInterface;
        this.proxyInstance = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{this.serviceInterface}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        final Future<Object> future = RpcInvoker.invoke(RpcRequest.builder()
                .id(id.incrementAndGet())
                .serviceName(serviceInterface.getName())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .parameters(args)
                .loadBalance("random")
                .cluster("failFast")
                .transport("http")
                .returnType(method.getReturnType())
                .build());
        return future.get(null == config || null == config.getTimeout() ? 2000 : config.getTimeout(), TimeUnit.MILLISECONDS);
    }

    public Object getProxyInstance() {
        return proxyInstance;
    }
}
