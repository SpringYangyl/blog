package com.yyl.config;

import com.yyl.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class ScheduledService {
    @Autowired
    ArticleService articleService;

    @Scheduled(fixedRate = 1000*60)
    public void task(){
        articleService.updateViewCount();
    }
}
