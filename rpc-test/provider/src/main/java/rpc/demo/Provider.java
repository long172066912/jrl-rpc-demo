package rpc.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc.anotation.RpcService;
import rpc.client.HelloRpcClient;

@RpcService
public class Provider implements HelloRpcClient {
    private Logger LOGGER = LoggerFactory.getLogger(Provider.class);

    @Override
    public String hello(String name) {
        LOGGER.info("say {}", name);
        return "say " + name;
    }
}
