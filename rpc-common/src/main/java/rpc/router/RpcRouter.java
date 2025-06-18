package rpc.router;

import rpc.transport.RpcRequest;

import java.util.List;

public interface RpcRouter<T> {
    /**
     * 根据服务名选择服务
     *
     * @param request
     * @return
     */
    List<T> select(RpcRequest request, List<T> servers);
}
