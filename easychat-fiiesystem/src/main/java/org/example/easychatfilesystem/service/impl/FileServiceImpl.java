package org.example.easychatfilesystem.service.impl;

import com.alibaba.nacos.api.utils.StringUtils;
import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import org.example.easychatcommon.exception.CustomException;
import org.example.easychatcommon.protocol.CommonCode;
import org.example.easychatcommon.protocol.ResultResponse;
import org.example.easychatfilesystem.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;


@Service
@Slf4j
public class FileServiceImpl implements FileService {
    @Value("${minio.endpoint}")
    private String endpoint;
    @Value("${minio.accesskey}")
    private String accessKey;
    @Value("${minio.secretkey}")
    private String secretKey;
    @Value("${minio.bucketName}")
    private String bucketName;

    //这里的路径是想要存储的位置
    public ResultResponse<Object> uploadFile(MultipartFile file, String filePath) throws CustomException {
        if (file.isEmpty() || StringUtils.isBlank(filePath)) {
            throw new CustomException(CommonCode.EMPTY_ERROR);
        }
        try (InputStream inputStream = file.getInputStream()) {
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint(endpoint)
                            .credentials(accessKey, secretKey)
                            .build();

            boolean isExist = minioClient
                    .bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!isExist) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(filePath)
                            .stream(inputStream, inputStream.available(), -1)
                            .build());

        } catch (Exception e) {
            e.printStackTrace();
            return ResultResponse.fail();
        }
        return ResultResponse.success();
    }

    public ResultResponse<Object> checkFile(String path) throws CustomException {
        if (StringUtils.isEmpty(path)) {
            throw new CustomException(CommonCode.EMPTY_ERROR);
        }
        try {
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint(endpoint)
                            .credentials(accessKey, secretKey)
                            .build();
            try {
                //这才是方法的关键
                //是在构造一个 StatObjectArgs 对象，用来告诉 MinIO：我要查看哪个桶(bucket)里的哪个对象(object)。
                minioClient.statObject(
                        StatObjectArgs.builder().bucket(bucketName).object(path).build());
            } catch (Exception e) {
                return ResultResponse.fail();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultResponse.success();
    }
}
