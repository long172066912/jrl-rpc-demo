package rpc.transport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author JerryLong
 * @Description: //TODO (用一句话描述该文件做什么)
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RpcRequest implements Serializable {
    private Long id;
    private String serviceName;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] parameters;
    private Class<?> returnType;
    private String cluster = "failFast";
    private String loadBalance = "random";
    private String transport = "http";
    private String registry;
}
