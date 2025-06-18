package rpc.transport.http;

import com.fasterxml.jackson.core.type.TypeReference;
import rpc.exception.RpcException;
import rpc.transport.RpcRequest;
import rpc.transport.RpcTransport;
import rpc.utils.HttpPostUtil;
import rpc.utils.json.JrlJsonUtil;

import java.lang.reflect.Type;
import java.util.concurrent.*;

public class RpcHttpRpcTransport implements RpcTransport {

    private static final ExecutorService POOL = new ThreadPoolExecutor(5, 20, 60, TimeUnit.SECONDS, new SynchronousQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());

    @Override
    public Future<Object> request(String ip, int port, RpcRequest request) throws Exception {
        final CompletableFuture<Object> future = new CompletableFuture<>();
        POOL.execute(() -> {
            try {
                final String s = HttpPostUtil.postJson("http://" + ip + ":" + port + "/rpc", JrlJsonUtil.toJson(request));
                //反序列化
                if (isWrapClass(request.getReturnType())) {
                    future.complete(s);
                } else {
                    future.complete(JrlJsonUtil.fromJson(s, new TypeReference<Object>() {
                        @Override
                        public Type getType() {
                            return request.getReturnType();
                        }
                    }));
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
