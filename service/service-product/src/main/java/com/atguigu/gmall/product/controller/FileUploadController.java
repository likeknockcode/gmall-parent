package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.common.result.ResultCodeEnum;
import com.atguigu.gmall.product.service.FileUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "文件上传接口")
public class FileUploadController {
    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("fileUpload")
    @ApiOperation("文件上传")
    public Result<String> fileUpLoad(MultipartFile file){
        String url = fileUploadService.fileUpLoad(file);
        return Result.build(url, ResultCodeEnum.SUCCESS);
    }
}
