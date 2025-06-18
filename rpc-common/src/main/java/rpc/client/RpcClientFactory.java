package rpc.client;

import rpc.config.ReferenceConfig;

import java.util.Map;

public class RpcClientFactory {

    private static final Map<String, RpcClientProxy> CLIENT_MAP = new java.util.concurrent.ConcurrentHashMap<>();

    public static <T> T getClient(Class<T> tClass) {
        final RpcClientProxy proxy = CLIENT_MAP.computeIfAbsent(tClass.getName(), key -> new RpcClientProxy(tClass, new ReferenceConfig()));
        return (T) proxy.getProxyInstance();
    }
}
