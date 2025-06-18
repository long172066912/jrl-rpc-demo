package rpc.router;

import rpc.registry.RpcRegistryAppInfo;
import rpc.transport.RpcRequest;

import java.util.List;

public class DefaultRpcRouter implements RpcRouter<RpcRegistryAppInfo> {

    @Override
    public List<RpcRegistryAppInfo> select(RpcRequest request, List<RpcRegistryAppInfo> servers) {
        return servers;
    }
}
