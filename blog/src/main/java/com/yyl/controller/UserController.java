package com.yyl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yyl.annotation.SysLog;
import com.yyl.domain.ResponseResult;
import com.yyl.entity.User;
import com.yyl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;

@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    /**
     *
     * @return
     */
    @GetMapping("userInfo")
    @SysLog(bussinessName = "userInfo")
    public ResponseResult userInfo(){
        ResponseResult result = userService.userInfo();
//        Object  obj =  JSON.toJSON(result.getData());
//        String s = JSONObject.toJSONString(obj);
//        kafkaTemplate.send("test2",s);
        return result;
    }

    /**
     *
     * @param user
     * @return
     */
    @PutMapping("userInfo")
    @Transactional
    @SysLog(bussinessName = "updateUserInfo")
    public ResponseResult updateUserInfo(@RequestBody User user){
        return userService.updateUserInfo(user);
    }

    /**
     *
     * @param user
     * @return
     */
    @PostMapping("register")
    @Transactional
    @SysLog(bussinessName = "register")
    public ResponseResult register(@RequestBody User user){
        return userService.register(user);
    }
}
