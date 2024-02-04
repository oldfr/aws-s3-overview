package com.example.awss3overview.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.PresignedUrlDownloadRequest;
import com.amazonaws.services.s3.model.PresignedUrlUploadRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface BucketService {

    List<Bucket> getBucketList();

    boolean validateBucket(String bucketName);

    void downloadObjectFromBucket(PresignedUrlDownloadRequest presignedUrlDownloadRequest, String bucketName) throws Exception;

    void uploadObjectToS3(PresignedUrlUploadRequest presignedUrlUploadRequest, String bucketName) throws Exception;

    void getObjectFromBucket(String bucketName, String objectName) throws IOException;

    void putObjectIntoBucket(String bucketName, String objectName, String filePathToUpload);

    void createBucket(String bucket);


}
