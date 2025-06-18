package rpc.client;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = RpcClientProperties.PREFIX)
public class RpcClientProperties {
    public static final String PREFIX = "rpc.client";
    private Boolean enabled = true;
}