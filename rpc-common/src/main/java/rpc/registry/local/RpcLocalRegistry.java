package rpc.registry.local;

import cn.hutool.core.io.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc.config.ServerConfig;
import rpc.config.ServiceConfig;
import rpc.registry.RpcRegistry;
import rpc.registry.RpcRegistryAppInfo;
import rpc.utils.json.JrlJsonNoExpUtil;
import rpc.utils.json.JrlJsonUtil;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class RpcLocalRegistry implements RpcRegistry {
    private Logger LOGGER = LoggerFactory.getLogger(RpcLocalRegistry.class);
    private static final RpcLocalRegistry INSTANCE = new RpcLocalRegistry();
    private static final String BASE_DIR = System.getProperty("user.dir") + "/registry";

    public static RpcLocalRegistry getInstance() {
        return INSTANCE;
    }

    @Override
    public void register(String app, String ip, int port, ServerConfig serverConfig, List<ServiceConfig> serviceConfigs) throws Exception {
        String fileName = getFileName(ip);
        String appDir = BASE_DIR + "/" + app;
        FileUtil.mkdir(appDir);

        String filePath = appDir + "/" + fileName;
        RpcRegistryAppInfo registryInfo = new RpcRegistryAppInfo(app, ip, port, serverConfig, serviceConfigs);

        String jsonContent = JrlJsonUtil.toJson(registryInfo);
        Files.write(new File(filePath).toPath(), jsonContent.getBytes(StandardCharsets.UTF_8));
        LOGGER.info("rpc register success, app : {}, ip : {}, port : {}, filePath : {}", app, ip, port, filePath);
    }

    @Override
    public void unRegister(String app, String ip) throws Exception {
        final String filePath = BASE_DIR + "/" + app + "/" + getFileName(ip);
        FileUtil.del(filePath);
        LOGGER.info("rpc register success, app : {}, ip : {} , filePath : {}", app, ip, filePath);
    }

    @Override
    public List<RpcRegistryAppInfo> getServiceRegistryAppInfo(String serviceName) {
        List<RpcRegistryAppInfo> serviceInfoList = new ArrayList<>();

        // 遍历所有应用目录
        for (File app : FileUtil.ls(BASE_DIR)) {
            if (app.isDirectory()) {
                for (File file : FileUtil.ls(app.getPath())) {
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

    private String getFileName(String ip) {
        return String.format("%s.txt", ip);
    }
}
