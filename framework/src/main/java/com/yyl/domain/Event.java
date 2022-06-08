package com.yyl.domain;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Event {
    //kafka主题
    private String topic;
    private Long userId;
    //事件类型,点赞和评论
    private String eventType;
    //文章id
    private Long articleId;
    private String content;

}
