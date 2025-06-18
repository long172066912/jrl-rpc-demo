package rpc.exception;

public class RpcException extends RuntimeException {
    public RpcException(String message) {
        super(message);
    }

    public RpcException(String msg, Exception e) {
        super(msg, e);
    }
}
