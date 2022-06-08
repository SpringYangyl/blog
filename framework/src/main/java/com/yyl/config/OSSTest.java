package com.yyl.config;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import java.io.ByteArrayInputStream;

public class OSSTest
{
//    public static void main(String[] args) {
//        String endpoint = "oss-cn-beijing.aliyuncs.com";
//        String keyid = "LTAI5t6QR8FF4u2FjYuA9ghf";
//        String keySecret = "uEydst0664UWivI0WnozfSSR5nhWPG";
//        String bucketName = "edu-138";
//        String objectName = "exampledir/exampleobject.txt";
//
//        // 创建OSSClient实例。
//        OSS ossClient = new OSSClientBuilder().build(endpoint, keyid, keySecret);
//
//        try {
//            // 填写字符串。
//            String content = "Hello OSS";
//
//            // 创建PutObjectRequest对象。
//            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, new ByteArrayInputStream(content.getBytes()));
//
//            // 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
//            // ObjectMetadata metadata = new ObjectMetadata();
//            // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
//            // metadata.setObjectAcl(CannedAccessControlList.Private);
//            // putObjectRequest.setMetadata(metadata);
//
//            // 上传字符串。
//            ossClient.putObject(putObjectRequest);
//        } catch (OSSException oe) {
//            System.out.println("Caught an OSSException, which means your request made it to OSS, "
//                    + "but was rejected with an error response for some reason.");
//            System.out.println("Error Message:" + oe.getErrorMessage());
//            System.out.println("Error Code:" + oe.getErrorCode());
//            System.out.println("Request ID:" + oe.getRequestId());
//            System.out.println("Host ID:" + oe.getHostId());
//        } catch (ClientException ce) {
//            System.out.println("Caught an ClientException, which means the client encountered "
//                    + "a serious internal problem while trying to communicate with OSS, "
//                    + "such as not being able to access the network.");
//            System.out.println("Error Message:" + ce.getMessage());
//        } finally {
//            if (ossClient != null) {
//                ossClient.shutdown();
//            }
//        }
//    }
}
