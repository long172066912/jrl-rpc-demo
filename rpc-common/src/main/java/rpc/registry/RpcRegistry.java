package rpc.registry;

import rpc.config.ServerConfig;
import rpc.config.ServiceConfig;
import rpc.registry.local.RpcLocalRegistry;

import java.util.List;

public interface RpcRegistry {
    /**
     * 注册应用以及服务配置
     *
     * @param app
     * @param ip
     * @param port
     * @param serverConfig
     * @param serviceConfigs
     * @throws Exception
     */
    void register(String app, String ip, int port, ServerConfig serverConfig, List<ServiceConfig> serviceConfigs) throws Exception;

    /**
     * 注销应用以及服务配置
     *
     * @param app
     * @param ip
     * @throws Exception
     */
    void unRegister(String app, String ip) throws Exception;

    /**
     * 根据服务名称获取服务提供者信息
     *
     * @param serviceName
     * @return
     */
    List<RpcRegistryAppInfo> getServiceRegistryAppInfo(String serviceName);

    /**
     * 获取默认的注册中心
     *
     * @return
     */
    public static RpcRegistry getDefaultRpcRegistry() {
        return RpcLocalRegistry.getInstance();
    }
}
