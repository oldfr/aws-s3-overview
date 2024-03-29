package com.example.awss3overview.controller;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.PresignedUrlDownloadRequest;
import com.example.awss3overview.service.BucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.List;

@RestController
@RequestMapping("/bucket")
public class BucketController {

    @Autowired
    BucketService bucketService;

    @GetMapping
    public void getBucketList() {
        List<Bucket> bucketList = bucketService.getBucketList();
        System.out.println("bucketList:"+bucketList);
    }

    @GetMapping("/downloadObj")
    public void downloadObject(@RequestParam("bucketName") String bucketName, @RequestParam("objName") String objName) throws Exception {
//        bucketService.downloadObjectFromBucket(new PresignedUrlDownloadRequest(new URL("")), bucketName);
        bucketService.getObjectFromBucket(bucketName, objName);
    }

    @PostMapping("/uploadObj")
    public void uploadObject(@RequestParam("bucketName") String bucketName, @RequestParam("objName") String objName) throws Exception {
//        bucketService.downloadObjectFromBucket(new PresignedUrlDownloadRequest(new URL("")), bucketName);
        bucketService.putObjectIntoBucket(bucketName, objName,"opt/test/v1/uploadfile.txt");
    }

    @PostMapping("/createBucket")
    public String createBucket(@RequestParam("bucketName") String bucketName) {
        bucketService.createBucket(bucketName);
        return "done";
    }
}
