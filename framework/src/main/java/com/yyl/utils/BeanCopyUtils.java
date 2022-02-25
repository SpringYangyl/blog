package com.yyl.utils;

import com.yyl.domain.vo.HotArticleVo;
import com.yyl.entity.Article;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtils {
    private BeanCopyUtils(){

    }
    public static<V> V copyBean(Object source, Class<V> clazz){
        //创建目标对象 实现属性拷贝
        V bean = null;
        try {
            bean = clazz.newInstance();
            BeanUtils.copyProperties(source,bean);
            return bean;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return bean;
    }
    public static <O,V> List<V> copyBeanList(List<O> list, Class<V> clazz){
        return list.stream()
                .map(o -> copyBean(o,clazz))
                .collect(Collectors.toList());
    }
//
//    public static void main(String[] args) {
//        Article article = new Article();
//        article.setId(1L);
//        article.setTitle("zx");
//        HotArticleVo hotArticleVo = copyBean(article, HotArticleVo.class);
//        System.out.println(hotArticleVo);
//    }
}
