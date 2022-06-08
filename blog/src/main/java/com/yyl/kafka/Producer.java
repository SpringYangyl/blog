package com.yyl.kafka;

import com.alibaba.fastjson.JSONObject;
import com.yyl.domain.Event;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Producer {
    @Autowired
    KafkaTemplate kafkaTemplate;
    //test2
    public  void sent(Event event){
        kafkaTemplate.send(event.getTopic(),JSONObject.toJSONString(event));
        //kafkaTemplate.send(event.getTopic(), JSONObject.toJSONString(event));
    }

}
