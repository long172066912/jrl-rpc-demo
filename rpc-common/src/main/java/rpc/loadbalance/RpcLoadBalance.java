package rpc.loadbalance;

import rpc.transport.RpcRequest;

import java.util.List;

public interface RpcLoadBalance<T> {
    /**
     * 根据服务名选择服务
     *
     * @param servers
     * @return
     */
    T select(List<T> servers);
}
