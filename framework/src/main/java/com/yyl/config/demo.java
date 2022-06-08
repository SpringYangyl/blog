package com.yyl.config;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.*;
public class demo {
//    public static void main(String[] args) throws InterruptedException {
//        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date = new Date();
//
//        System.out.println(simpleDateFormat1.format(date));
////        task1 task1 = new task1();
//
////        new Thread(task1, "t1").start();
////        new Thread(task1,"t2").start();
////        new Thread(task1,"t3").start();
////        task2 task2 = new task2();
////        Thread t1 = new Thread(task2, "t1");
////        Thread t2 = new Thread(task2, "t2");
////        t1.start();
////        t1.join();
////        t2.start();
////
////        System.out.println("main");
//    }

    List<String> res = new ArrayList<>();
    public List<String> generateParenthesis(int n) {
        if(n <= 0){
            return res;
        }
        generateRes("",n,n);
        return res;

    }
    public void generateRes(String str, int left,int right){
        if(left == 0 && right == 0){
            res.add(str);
            return;
        }
        if(left == right){
            generateRes(str+"(",left - 1,right);
        }else if (left < right){
            if(left > 0){
                generateRes(str+"(",left - 1,right);
            }
            generateRes(str+")",left,right - 1);
        }
    }
    public int subarraySum(int[] nums, int k) {
        Map<Integer,Integer> map = new HashMap<>();
        map.put(0,1);
        int sum = 0;
        int cnt = 0;
        for(int i = 0;i<nums.length;i++){
            sum += nums[i];
            if(map.containsKey(sum-k)){
                cnt += map.get(sum-k);
            }
            map.put(sum,map.getOrDefault(sum,0)+1);
        }
        return cnt;
    }

    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        int[] res = new int[n-k+1];
        int index = 0;
        ArrayDeque<Integer> que = new ArrayDeque<>();
        for(int i = 0; i < n; i++){
            while(!que.isEmpty() && que.peek()<i-k+1){
                que.poll();
            }
            while(!que.isEmpty() && nums[que.peekLast()]<nums[i]){
                que.pollLast();
            }
            que.offer(i);
            if(i>=k-1){
                res[index++] = nums[que.peek()];
            }
        }

        return res;
}

}
class task2 implements Runnable{

    @SneakyThrows
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName());
        }
    }
}
@Slf4j
class task1 implements Runnable{
    int i = 1;


    @Override
    public void run() {
        while (i<=30){
            synchronized (this){
                notify();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(i % 3 ==1){
                    log.info("A");
                }else if(i % 3 ==2){
                    log.info("B");
                }else{
                    log.info("C");
                }
                i++;
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}