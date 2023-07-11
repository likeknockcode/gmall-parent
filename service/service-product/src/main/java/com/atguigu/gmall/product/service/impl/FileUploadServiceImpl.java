package com.atguigu.gmall.product.service.impl;

import com.atguigu.common.config.MinioConfigration;
import com.atguigu.common.properties.MiniProperties;
import com.atguigu.gmall.product.service.FileUploadService;
import io.minio.*;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @Description：文件上传实现类
 * @Author: scv
 * @CreateTime: 2023-07-11  19:46
 * @Version: 1.0
 */
@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Autowired
    private MiniProperties miniProperties;

    @Autowired
    private MinioConfigration minioConfigration;
    @Autowired
    private MinioClient minioClient;
    @Override
    public String fileUpLoad(MultipartFile file) {
        try {
            //判断是否存在桶，不存在则创建
            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket(miniProperties.getBucketName()).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(miniProperties.getBucketName()).build());
            } else {
                System.out.println("Bucket 'asiatrip' already exists.");
            }
            //UUID获取文件名
            String fileName = UUID.randomUUID().toString().replace("-","");
            //使用工具类获取文件后缀
            String extensionFileName = FilenameUtils.getExtension(file.getOriginalFilename());
            //上传对象
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(miniProperties.getBucketName())
                    .stream(file.getInputStream(),file.getSize(),-1)
                    .object(fileName +"."+ extensionFileName)
                    .build();

            minioClient.putObject(putObjectArgs);
            String url = "http://192.168.206.120:9000/gmall/"+fileName +"."+ extensionFileName;
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
