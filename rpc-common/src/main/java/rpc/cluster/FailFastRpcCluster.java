package rpc.cluster;

import rpc.exception.RpcException;
import rpc.registry.RpcRegistryAppInfo;
import rpc.transport.RpcRequest;

import java.util.List;
import java.util.concurrent.Future;
import java.util.function.BiFunction;

public class FailFastRpcCluster<R> implements RpcCluster<R> {
    @Override
    public R invoke(RpcRequest request, List<RpcRegistryAppInfo> apps, BiFunction<RpcRequest, List<RpcRegistryAppInfo>, Future<R>> handler) {
        final Future<R> future = handler.apply(request, apps);
        try {
            return future.get();
        } catch (Exception e) {
            throw new RpcException("rpc invoke error ! requestId " + request.getId(), e);
        }
    }
}
