package rpc.demo;

import org.springframework.stereotype.Component;
import rpc.anotation.RpcReference;
import rpc.client.HelloRpcClient;

@Component
public class Consumer {
    @RpcReference
    private HelloRpcClient helloRpcClient;

    public void test() {
        System.out.println(helloRpcClient.hello("hello"));
    }
}
