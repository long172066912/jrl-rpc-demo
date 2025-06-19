package rpc.client;

public interface HelloRpcClient {
    /**
     * say hello
     *
     * @param name
     * @return
     */
    String hello(String name);
}
