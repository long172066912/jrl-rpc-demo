package rpc.transport.http;

import rpc.exception.RpcException;
import rpc.transport.RpcRequest;
import rpc.transport.RpcResponse;
import rpc.transport.RpcTransport;
import rpc.utils.HttpPostUtil;
import rpc.utils.json.JrlJsonUtil;

import java.util.concurrent.*;

public class RpcHttpRpcTransport implements RpcTransport {

    private static final ExecutorService POOL = new ThreadPoolExecutor(5, 20, 60, TimeUnit.SECONDS, new SynchronousQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());

    @Override
    public Future<Object> request(String ip, int port, RpcRequest request) throws Exception {
        final CompletableFuture<Object> future = new CompletableFuture<>();
        POOL.execute(() -> {
            try {
                final String s = HttpPostUtil.postJson("http://" + ip + ":" + port + "/rpc", JrlJsonUtil.toJson(request));
                RpcResponse rpcResponse = JrlJsonUtil.fromJson(s, RpcResponse.class);
                if (isWrapClass(request.getReturnType())) {
                    future.complete(rpcResponse.getData());
                } else {
                    //反序列化
                    future.complete(rpcResponse.getData());
                }
            } catch (Exception e) {
                future.completeExceptionally(new RpcException("rpc request error !", e));
            }
        });
        return future;
    }

    private static boolean isWrapClass(Class clz) {
        try {
            return clz.isEnum() || Object.class == clz || clz == String.class || clz.isPrimitive() || ((Class) clz.getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }
}
