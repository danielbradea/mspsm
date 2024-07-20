package com.bid90.psm.service;

import com.bid90.psm.exceptions.MinioRuntimeException;
import io.minio.*;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class MinioService {


    private final MinioClient minioClient;

    @Value("${minio.bucketName}")
    private String bucketName;

    public MinioService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public void uploadFile(MultipartFile file, String newFileName) {
        try (InputStream inputStream = file.getInputStream()) {
            var poa = PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(file.getOriginalFilename())
                    .stream(inputStream, file.getSize(), -1)
                    .contentType(file.getContentType());
            if(newFileName != null){
                poa.object(newFileName);
            }
            minioClient.putObject(poa.build());
        } catch (Exception e) {
            throw new MinioRuntimeException(e.getMessage(),e.getCause());
        }
    }

    public InputStream downloadFile(String fileName) {
        try {
            return  minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .build()
            );
        } catch (Exception e) {
            throw new MinioRuntimeException(e.getMessage(),e.getCause());
        }
    }

    public void deleteFile(String fileName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .build()
            );
        } catch (Exception e) {
            throw new MinioRuntimeException(e.getMessage(),e.getCause());
        }
    }
}