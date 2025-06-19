package rpc.invoker;

import rpc.cluster.FailFastRpcCluster;
import rpc.cluster.RpcCluster;
import rpc.exception.RpcException;
import rpc.loadbalance.DefaultRpcLoadBalance;
import rpc.loadbalance.RpcLoadBalance;
import rpc.registry.RpcRegistry;
import rpc.registry.RpcRegistryAppInfo;
import rpc.router.DefaultRpcRouter;
import rpc.router.RpcRouter;
import rpc.transport.RpcRequest;
import rpc.transport.RpcTransport;
import rpc.transport.http.RpcHttpRpcTransport;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

/**
 * 集群处理链
 *
 * @author JerryLong
 */
public class RpcInvoker {

    private static final Map<String, RpcCluster<Future<Object>>> FAULT_TOLERANT_MAP = new ConcurrentHashMap<>();
    private static final Map<String, RpcLoadBalance<RpcRegistryAppInfo>> LOAD_BALANCE_MAP = new ConcurrentHashMap<>();
    private static final Map<String, RpcRouter<RpcRegistryAppInfo>> ROUTER_MAP = new ConcurrentHashMap<>();
    private static final Map<String, RpcTransport> TRANSPORT_MAP = new ConcurrentHashMap<>();

    static {
        addRpcCluster("failFast", new FailFastRpcCluster());
        addRpcRouter("default", new DefaultRpcRouter());
        addRpcLoadBalance("random", new DefaultRpcLoadBalance());
        addRpcTransport("http", new RpcHttpRpcTransport());
    }

    public static Future<Object> invoke(RpcRequest request) throws Exception {
        //1 获取容错
        RpcCluster<Future<Object>> faultTolerant = FAULT_TOLERANT_MAP.get(request.getCluster());
        //2 获取router，router本身可能是个链
        RpcRouter<RpcRegistryAppInfo> router = ROUTER_MAP.get("default");
        //3 获取loadbalance
        RpcLoadBalance<RpcRegistryAppInfo> loadBalance = LOAD_BALANCE_MAP.get(request.getLoadBalance());
        //4 获取传输管道
        RpcTransport transport = TRANSPORT_MAP.get(request.getTransport());
        final RpcRegistry registry = RpcRegistry.getDefaultRpcRegistry();
        return faultTolerant.invoke(
                request,
                registry.getServiceRegistryAppInfo(request.getServiceName()),
                (request1, servers) -> {
                    final RpcRegistryAppInfo appInfo = loadBalance.select(router.select(request1, servers));
                    if (null == appInfo) {
                        throw new RpcException("loadBalance select is null");
                    }
                    try {
                        return transport.request(appInfo.getIp(), appInfo.getPort(), request1);
                    } catch (Exception e) {
                        throw new RpcException("rpc request error", e);
                    }
                }
        );
    }

    protected static void addRpcCluster(String clusterName, RpcCluster cluster) {
        FAULT_TOLERANT_MAP.put(clusterName, cluster);
    }

    protected static void addRpcRouter(String routerName, RpcRouter router) {
        ROUTER_MAP.put(routerName, router);
    }

    protected static void addRpcLoadBalance(String loadBalanceName, RpcLoadBalance loadBalance) {
        LOAD_BALANCE_MAP.put(loadBalanceName, loadBalance);
    }

    protected static void addRpcTransport(String transportName, RpcTransport transport) {
        TRANSPORT_MAP.put(transportName, transport);
    }
}
