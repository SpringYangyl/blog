package com.yyl.kafka;

import com.alibaba.fastjson.JSONObject;
import com.yyl.domain.Event;
import com.yyl.utils.RedisCache;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class Consumer {
    @Autowired
    RedisCache redisCache;

    @KafkaListener(topics = {"comment1","like1"},groupId = "test1")
    public  void consumer(ConsumerRecord record) throws InterruptedException {
       Event event = JSONObject.parseObject(record.value().toString(),Event.class);
       redisCache.setCacheMapValue(event.getTopic(),event.getUserId().toString()+":"+event.getArticleId(),event);
    }
}
