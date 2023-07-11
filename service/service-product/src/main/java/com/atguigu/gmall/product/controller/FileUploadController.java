package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.common.result.ResultCodeEnum;
import com.atguigu.gmall.product.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description：文件上传
 * @Author: scv
 * @CreateTime: 2023-07-11  19:41
 * @Version: 1.0
 */
@RestController
@RequestMapping("/admin/product")
public class FileUploadController {
    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("fileUpload")
    public Result<String> fileUpLoad(MultipartFile file){
        String url = fileUploadService.fileUpLoad(file);
        return Result.build(url, ResultCodeEnum.SUCCESS);
    }
}
