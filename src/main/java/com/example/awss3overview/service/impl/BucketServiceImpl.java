package com.example.awss3overview.service.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.example.awss3overview.service.BucketService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class BucketServiceImpl implements BucketService {

    Logger LOG = LogManager.getLogger(BucketServiceImpl.class);

    @Autowired
    AmazonS3 s3Client;


    @Override
    public List<Bucket> getBucketList() {
        LOG.info("getting bucket list... ");
        return s3Client.listBuckets();
    }

    @Override
    public boolean validateBucket(String bucketName) {
        List<Bucket> bucketList = getBucketList();
        LOG.info("Bucket list:"+bucketList);
        return bucketList.stream().anyMatch(m -> bucketName.equals(m.getName()));
    }

    @Override
    public void downloadObjectFromBucket(PresignedUrlDownloadRequest presignedUrlDownloadRequest, String bucketName) throws Exception {
        if(!validateBucket(bucketName)) {
            LOG.info("invalid bucket");
            throw new Exception("Invalid bucket");
        }
        LOG.info("downloading object...");
        s3Client.download(presignedUrlDownloadRequest,new File("opt/test/v1.txt"));
    }

    @Override
    public void getObjectFromBucket(String bucketName, String objectName) throws IOException {
        S3Object s3Object = s3Client.getObject(bucketName, objectName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        FileOutputStream fos = new FileOutputStream(new File("opt/test/v1/"+objectName));
        byte[] read_buf = new byte[1024];
        int read_len = 0;
        while ((read_len = inputStream.read(read_buf)) > 0) {
            fos.write(read_buf, 0, read_len);
        }
        inputStream.close();
        fos.close();
    }

    @Override
    public void putObjectIntoBucket(String bucketName, String objectName, String filePathToUpload) {
        try {
            s3Client.putObject(bucketName, objectName, new File(filePathToUpload));
        } catch (AmazonServiceException e) {
            LOG.info(e.getErrorMessage());
        }
    }

    @Override
    public void createBucket(String bucketName) {
        s3Client.createBucket(bucketName);
    }

    @Override
    public void uploadObjectToS3(PresignedUrlUploadRequest presignedUrlUploadRequest, String bucketName) throws Exception {
        if(!validateBucket(bucketName)) {
            LOG.info("invalid bucket");
            throw new Exception("Invalid bucket exception");
        }
        LOG.info("uploading object...");
        s3Client.upload(presignedUrlUploadRequest);
    }


}
