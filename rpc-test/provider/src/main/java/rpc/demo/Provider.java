package rpc.demo;

import rpc.client.HelloRpcClient;

public class Provider implements HelloRpcClient {
    @Override
    public String hello(String name) {
        return "say " + name;
    }
}
