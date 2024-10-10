package com.dogmeat.petconnect.petregister.service;

import java.io.File;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.dogmeat.petconnect.petregister.mapper.PetConnectMapper;

@Service
public class PetConnectService {

    @Autowired
    private PetConnectMapper petConnectMapper;

    @Autowired
    private AmazonS3Client amazonS3Client;

    @Value("${aws.s3.bucket}")
    private String bucket;

    public PutObjectResult uploadFile(File file, String fileName) {
        return amazonS3Client.putObject(bucket, fileName, file);
    }

    public int addOrphan(Map<String, Object> orphan) {
        return petConnectMapper.addOrphan(orphan);
    }
}
