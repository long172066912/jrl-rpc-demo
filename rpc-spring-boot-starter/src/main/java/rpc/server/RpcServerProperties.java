package rpc.server;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = RpcServerProperties.PREFIX)
public class RpcServerProperties {
    public static final String PREFIX = "rpc.server";
    private Boolean enabled = true;
    private String app = "default";
    private Integer port = 8080;
}