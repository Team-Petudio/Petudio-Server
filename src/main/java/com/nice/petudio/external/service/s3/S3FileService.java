package com.nice.petudio.external.service.s3;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import java.net.URL;
import java.util.Date;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class S3FileService {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${cloud.aws.s3.path}")
    private String s3DefaultPath;
    private static final String BUCKET_DIRECTORY_NAME = "petProfile";
    private static final String FILE_NAME_PREFIX = "Petudio_";


    /**
     * presigned url 발급
     *
     * @param s3DirectoryPath 파일을 저장할 S3 디렉토리 경로
     * @param index           이미지 순서
     * @return presigned url
     */
    public String getPreSignedUrl(String s3DirectoryPath, int index) {
        String fileName = createFilePath(s3DirectoryPath, index);

        GeneratePresignedUrlRequest generatePresignedUrlRequest = getGeneratePreSignedUrlRequest(bucket, fileName);
        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }

    public String getImageUri(String filePath) {
        return s3DefaultPath + filePath;
    }

    /**
     * 파일 업로드용(PUT) presigned url 생성
     *
     * @param bucket   버킷 이름
     * @param fileName S3 업로드용 파일 이름
     * @return presigned url
     */
    private GeneratePresignedUrlRequest getGeneratePreSignedUrlRequest(String bucket, String fileName) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucket, fileName)
                        .withMethod(HttpMethod.PUT)
                        .withExpiration(getPreSignedUrlExpiration());
        generatePresignedUrlRequest.addRequestParameter(
                Headers.S3_CANNED_ACL,
                CannedAccessControlList.PublicRead.toString());
        return generatePresignedUrlRequest;
    }

    /**
     * presigned url 유효 기간 설정
     *
     * @return 유효기간
     */
    private Date getPreSignedUrlExpiration() {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 2;
        expiration.setTime(expTimeMillis);
        return expiration;
    }

    /**
     * 파일 고유 ID를 생성
     *
     * @return 36자리의 UUID
     */
    private String createFileId() {
        return UUID.randomUUID().toString();
    }

    /**
     * 파일의 전체 경로를 생성
     *
     * @param index 이미지 순서
     * @return 파일의 전체 경로
     */
    private String createFilePath(String directoryPath, int index) {
        // BUCKET_DIRECTORY_NAME/memberId/fileId+fileName
        return String.format("%s/%d", directoryPath, index);
    }

    /**
     * 파일을 저장할 디렉토리 경로를 생성
     *
     * @param memberId 회원 Primary Key
     * @return 파일의 전체 경로
     */
    public String createS3DirectoryPath(Long memberId) {
        return String.format("%s/%d/%s", BUCKET_DIRECTORY_NAME, memberId, FILE_NAME_PREFIX + createFileId());
    }
}
