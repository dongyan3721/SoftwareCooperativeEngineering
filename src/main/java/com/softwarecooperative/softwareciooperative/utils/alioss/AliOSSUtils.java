package com.softwarecooperative.softwareciooperative.utils.alioss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.UUID;

/**
 * 阿里云 OSS 工具类
 */
@Component
public class AliOSSUtils {
    @Autowired
    private AliOSSProperties aliOSSProperties;

    /**
     * 实现上传图片到OSS
     */
    public String upload(MultipartFile file) throws IOException {
        // 获取阿里云OSS参数
        String endpoint = aliOSSProperties.getEndpoint();
        String bucketName = aliOSSProperties.getBucketName();
        String accessKeyId = aliOSSProperties.getAccessKeyId();
        String accessKeySecret = aliOSSProperties.getAccessKeySecret();

        //文件访问路径
        String url;
        // 获取上传的文件的输入流
        try (InputStream inputStream = file.getInputStream()) {
            // 避免文件覆盖
            String originalFilename = file.getOriginalFilename();
            originalFilename = originalFilename != null && !originalFilename.isEmpty() ? file.getOriginalFilename() : file.getName();
            String fileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));

            //上传文件到 OSS
            // TODO client暂时是随用随关的，以后可以用连接池优化
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            ossClient.putObject(bucketName, fileName, inputStream);

            // TODO 读写权限暂时设置为公共读，学懂sts之后可以用临时授权进行权限控制
            url = endpoint.split("//")[0] + "//" + bucketName + "." + endpoint.split("//")[1] + "/" + fileName;
            // 关闭ossClient
            ossClient.shutdown();
        }
        return url;// 把上传到oss的路径返回
    }

}
