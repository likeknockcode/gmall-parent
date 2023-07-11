package com.atguigu.gmall.product;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;

/**
 * @Description：测试Minio
 * @Author: scv
 * @CreateTime: 2023-07-11  19:27
 * @Version: 1.0
 */

public class Test {
    public static void main(String[] args) throws Exception{
        // 创建minio客户端
        MinioClient minioClient =
                MinioClient.builder()
                        .endpoint("http://192.168.206.120:9000")
                        .credentials("admin", "admin123456")
                        .build();

        //判断是否存在桶，不存在则创建
        boolean found =
                minioClient.bucketExists(BucketExistsArgs.builder().bucket("gmall").build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket("gmall").build());
        } else {
            System.out.println("Bucket 'asiatrip' already exists.");
        }

       //上传对象
        minioClient.uploadObject(
                UploadObjectArgs.builder()
                        .bucket("gmall")
                        .object("scv.png")
                        .filename("D:\\1.png")
                        .build());
        String url = "http://192.168.206.120:9000/gmall/scv.png";
        System.out.println(url);
    }
}
