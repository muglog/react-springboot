package com.muglog.utils;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.muglog.common_enum.FileType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class FileUtil {

    private static AmazonS3Client amazonS3Client;
    private static String bucket;

    @Autowired
    public void setAmazonS3Client(AmazonS3Client amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }
    @Value("${cloud.aws.s3.bucket}")
    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public static String uploadFiles(MultipartFile multipartFile, FileType fileType) throws IOException {
        File uploadFile = convert(multipartFile);
        return upload(uploadFile, getFilePath(fileType));
    }

    private static String getFilePath(FileType fileType) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String datePath = sdf.format(new Date());

        return switch (fileType){
            case REVIEW_PHOTO -> "REVIEW_PHOTO/";
            case THUMBNAIL_PHOTO -> "THUMBNAIL_PHOTO/";
            default -> "ETC";
        } + datePath;
    }

    public static String upload(File uploadFile, String filePath) {
        String fileName = filePath + "/" + uploadFile.getName();
        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    private static String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(
                new PutObjectRequest(bucket, fileName, uploadFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private static void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            System.out.println("File delete success");
            return;
        }
        System.out.println("File delete fail");
    }

    private static File convert(MultipartFile file) throws IOException {
        File convertFile = new File(System.getProperty("user.dir") + "/" + UUID.randomUUID());
        if (convertFile.createNewFile()) {
            try (FileOutputStream fileOutputStream = new FileOutputStream(convertFile)) {
                fileOutputStream.write(file.getBytes());
            }
            return convertFile;
        }
        return null;
    }

}
