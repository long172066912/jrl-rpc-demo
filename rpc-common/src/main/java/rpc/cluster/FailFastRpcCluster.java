package rpc.cluster;

import rpc.registry.RpcRegistryAppInfo;
import rpc.transport.RpcRequest;

import java.util.List;
import java.util.function.BiFunction;

public class FailFastRpcCluster<R> implements RpcCluster<R> {
    @Override
    public R invoke(RpcRequest request, List<RpcRegistryAppInfo> apps, BiFunction<RpcRequest, List<RpcRegistryAppInfo>, R> handler) {
        return handler.apply(request, apps);
    }
}
