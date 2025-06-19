package rpc.demo;

import rpc.anotation.RpcService;
import rpc.client.HelloRpcClient;

@RpcService
public class Provider implements HelloRpcClient {
    @Override
    public String hello(String name) {
        return "say " + name;
    }
}
