package rpc.loadbalance;

import rpc.registry.RpcRegistryAppInfo;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RpcLoadBalanceRandom implements RpcLoadBalance<RpcRegistryAppInfo> {

    @Override
    public RpcRegistryAppInfo select(List<RpcRegistryAppInfo> servers) {
        if (servers.size() == 0) {
            return null;
        }
        if (servers.size() == 1) {
            return servers.get(0);
        }
        return servers.get(ThreadLocalRandom.current().nextInt(0, servers.size()));
    }
}
