package rpc.cluster;

import rpc.registry.RpcRegistryAppInfo;
import rpc.transport.RpcRequest;

import java.util.List;
import java.util.concurrent.Future;
import java.util.function.BiFunction;

public interface RpcCluster<T> {
    /**
     * 执行请求
     *
     * @param request
     * @param apps
     * @param handler
     * @return
     */
    T invoke(RpcRequest request, List<RpcRegistryAppInfo> apps, BiFunction<RpcRequest, List<RpcRegistryAppInfo>, Future<T>> handler);
}
