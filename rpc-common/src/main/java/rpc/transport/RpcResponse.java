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
public class RpcResponse implements Serializable {
    private Long id;
    private Object data;
    private Throwable exception;
}
