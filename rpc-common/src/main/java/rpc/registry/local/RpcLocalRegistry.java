package rpc.registry.local;

import cn.hutool.core.io.FileUtil;
import rpc.config.ServerConfig;
import rpc.config.ServiceConfig;
import rpc.registry.RpcRegistry;
import rpc.registry.RpcRegistryAppInfo;
import rpc.utils.json.JrlJsonNoExpUtil;
import rpc.utils.json.JrlJsonUtil;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class RpcLocalRegistry implements RpcRegistry {

    private static final RpcLocalRegistry INSTANCE = new RpcLocalRegistry();
    private static final String BASE_DIR = "registry"; // 存储目录

    public static RpcLocalRegistry getInstance() {
        return INSTANCE;
    }

    @Override
    public void register(String app, String ip, int port, ServerConfig serverConfig, List<ServiceConfig> serviceConfigs) throws Exception {
        String fileName = String.format("%s_%d.json", ip, port);
        String appDir = BASE_DIR + "/" + app;
        FileUtil.mkdir(appDir);

        String registryFilePath = appDir + "/" + fileName;
        RpcRegistryAppInfo registryInfo = new RpcRegistryAppInfo(app, ip, port, serverConfig, serviceConfigs);

        String jsonContent = JrlJsonUtil.toJson(registryInfo);
        FileUtil.writeString(registryFilePath, jsonContent, StandardCharsets.UTF_8);
    }

    @Override
    public void unRegister(String app, String ip) throws Exception {
        String appDir = BASE_DIR + "/" + app;
        if (FileUtil.exist(appDir)) {
            for (File file : FileUtil.ls(appDir)) {
                if (file.getName().startsWith(ip + "_")) {
                    FileUtil.del(file);
                }
            }
        }
    }

    @Override
    public List<RpcRegistryAppInfo> getServiceRegistryAppInfo(String serviceName) {
        List<RpcRegistryAppInfo> serviceInfoList = new ArrayList<>();

        // 遍历所有应用目录
        for (File app : FileUtil.ls(BASE_DIR)) {
            if (app.isDirectory()) {
                String appDir = BASE_DIR + "/" + app;
                for (File file : FileUtil.ls(appDir)) {
                    String jsonContent = FileUtil.readString(file, StandardCharsets.UTF_8);
                    RpcRegistryAppInfo registryInfo = JrlJsonNoExpUtil.fromJson(jsonContent, RpcRegistryAppInfo.class);
                    if (null == registryInfo) {
                        continue;
                    }
                    // 检查服务配置是否包含指定服务
                    for (ServiceConfig serviceConfig : registryInfo.getServiceConfigs()) {
                        if (serviceConfig.getServiceName().equals(serviceName)) {
                            serviceInfoList.add(new RpcRegistryAppInfo(
                                    registryInfo.getApp(),
                                    registryInfo.getIp(),
                                    registryInfo.getPort(),
                                    registryInfo.getServerConfig(),
                                    registryInfo.getServiceConfigs()
                            ));
                        }
                    }
                }
            }
        }

        return serviceInfoList;
    }
}
