package com.yyl.controller;

import com.yyl.annotation.SysLog;
import com.yyl.domain.ResponseResult;
import com.yyl.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    /**
     *
     * @return
     */
    @GetMapping("/getAllLink")
    @SysLog(bussinessName = "getAllLink")
    public ResponseResult getAllLink(){
        return linkService.getAllLink();
    }
}
