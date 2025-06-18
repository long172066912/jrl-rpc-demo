package rpc.transport;

import java.util.concurrent.Future;

public interface RpcTransport {

    /**
     * 发送请求
     *
     * @param ip      ip
     * @param port    端口
     * @param request 请求
     * @return 响应
     * @throws Exception 异常
     */
    Future<Object> request(String ip, int port, RpcRequest request) throws Exception;
}
