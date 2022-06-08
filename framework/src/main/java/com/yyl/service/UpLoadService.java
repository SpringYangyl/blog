package com.yyl.service;

import com.yyl.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface UpLoadService {
    ResponseResult uploadImg(MultipartFile img);
}
