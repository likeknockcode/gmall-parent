package com.atguigu.gmall.product.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Description：文件上传service
 * @Author: scv
 * @CreateTime: 2023-07-11  19:44
 * @Version: 1.0
 */
public interface FileUploadService {
    String fileUpLoad(MultipartFile file);
}
