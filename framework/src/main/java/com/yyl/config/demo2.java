package com.yyl.config;

import com.yyl.entity.Article;
import lombok.SneakyThrows;

import java.util.HashMap;

public class demo2 {
    public static void main(String[] args) {
        HashMap<Long, Long> map = new HashMap<>();
        map.put(1L,1L);
        map.put(2L,2L);
        map.forEach((k,v)->{
            Article article = new Article();
            article.setId(k);
            article.setViewCount(v);
        });
    }

}
class Mytask implements Runnable{
    private static final String str = "ABCDEFG";
    private static final Object lock = new Object();
    private static final int Num = 3;
    int length = str.length();
    private static int count = 0;
    private int num;
    private int num1 = 0;
    public Mytask(int num) {
        this.num = num;
    }

    @Override
    public void run() {
        while (true){
            synchronized (lock){
                lock.notifyAll();
                if(num1 == Num){
                    break;
                }
                if(count == length){
                    count = 0;
                    num1++;
                }
                if(count % length == this.num){
                    System.out.println(Thread.currentThread().getName());
                    System.out.println(str.charAt(count));
                    count++;

                }else {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }
}