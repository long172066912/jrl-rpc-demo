package rpc.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;
import rpc.registry.RpcRegistry;
import rpc.utils.IpUtils;


@Component
public class RpcServerShutdownListener implements ApplicationListener<ContextClosedEvent> {

    private Logger LOGGER = LoggerFactory.getLogger(RpcServerShutdownListener.class);

    @Autowired
    private RpcServerProperties rpcServerProperties;

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        try {
            RpcRegistry.getDefaultRpcRegistry().unRegister(rpcServerProperties.getApp(), IpUtils.IP_LAN);
            LOGGER.info("rpc unRegister success");
        } catch (Exception e) {
            LOGGER.error("rpc unRegister error", e);
        }
    }

}
