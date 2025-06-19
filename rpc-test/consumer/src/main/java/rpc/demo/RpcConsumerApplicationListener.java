package rpc.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class RpcConsumerApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private Consumer consumer;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        consumer.test();
    }
}