package rpc.registry;

import rpc.config.ServerConfig;
import rpc.config.ServiceConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RpcRegistryAppInfo {
    private String app;
    private String ip;
    private int port;
    private ServerConfig serverConfig;
    private List<ServiceConfig> serviceConfigs;
}
