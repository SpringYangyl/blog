package com.yyl.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ArticleLike {
    private String ArticleId;
    private String UserId;
    private Integer status;


}
