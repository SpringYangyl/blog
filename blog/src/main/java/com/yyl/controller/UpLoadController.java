package com.yyl.controller;

import com.yyl.annotation.SysLog;
import com.yyl.domain.ResponseResult;
import com.yyl.service.UpLoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UpLoadController {
    @Autowired
    private UpLoadService upLoadService;

    /**
     *
     * @param img
     * @return
     */
    @SysLog(bussinessName = "uploadImg")
    @PostMapping("upload")
    @Transactional
    public ResponseResult uploadImg(MultipartFile img){
        return upLoadService.uploadImg(img);
    }
}
