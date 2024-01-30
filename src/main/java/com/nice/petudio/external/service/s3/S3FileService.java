package com.nice.petudio.external.service.s3;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.nice.petudio.common.exception.error.ErrorCode;
import com.nice.petudio.common.exception.model.BadGatewayException;
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
    private String bucketName;
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
        try {
            String filePath = createFilePath(s3DirectoryPath, index);

            GeneratePresignedUrlRequest generatePresignedUrlRequest = getGeneratePreSignedUrlRequest(bucketName,
                    filePath);
            URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
            return url.toString();
        } catch (Exception exception) {
            throw new BadGatewayException(ErrorCode.BAD_GATEWAY_EXCEPTION,
                    String.format("S3 버킷의 디렉토리(%s/%d) 에 대한 PreSignedURL을 생성하는 과정에서 에러가 발생하였습니다.", s3DirectoryPath, index));
        }
    }

    /**
     * 파일의 전체 경로를 생성
     *
     * @param index 이미지 순서
     * @return 파일의 전체 경로
     */
    private String createFilePath(String directoryPath, int index) {
        // 경로: {BUCKET_DIRECTORY_NAME}/{memberId}/Petudio_{fileId}/{index}
        return String.format("%s/%d", directoryPath, index);
    }

    /**
     * 파일 업로드용(PUT) presigned url 생성
     *
     * @param bucket   버킷 이름
     * @param filePath S3 업로드용 파일 경로
     * @return presigned url
     */
    private GeneratePresignedUrlRequest getGeneratePreSignedUrlRequest(String bucket, String filePath) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucket, filePath)
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
     * 파일을 저장할 디렉토리 경로를 생성
     *
     * @param memberId 회원 Primary Key
     * @return 파일의 전체 경로
     */
    public String createS3DirectoryPath(Long memberId) {
        return String.format("%s/%d/%s", BUCKET_DIRECTORY_NAME, memberId, FILE_NAME_PREFIX + createFileId());
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
     * s3 파일 접근 URI 생성
     *
     * @param filePath S3 업로드용 파일 경로
     * @return s3 파일 접근 URI
     */
    public String createImageUri(String filePath) {
        return s3DefaultPath + filePath;
    }


    /**
     * s3 Directory 데이터 삭제
     *
     * @param s3DirectoryPath 파일 삭제를 위한 S3 디렉토리 경로
     */
    public void deleteImagesByS3DirectoryPath(String s3DirectoryPath) {
        try {
            // s3DirectoryPath에 속한 객체들 나열
            ListObjectsV2Request listObjectsRequest = new ListObjectsV2Request()
                    .withBucketName(bucketName)
                    .withPrefix(s3DirectoryPath);
            ListObjectsV2Result objectsResult = amazonS3.listObjectsV2(listObjectsRequest);

            // s3DirectoryPath 내의 객체들 삭제
            for (S3ObjectSummary objectSummary : objectsResult.getObjectSummaries()) {
                String key = objectSummary.getKey();
                amazonS3.deleteObject(new DeleteObjectRequest(bucketName, key));
            }
        } catch (Exception exception) {
            throw new BadGatewayException(ErrorCode.BAD_GATEWAY_EXCEPTION,
                    String.format("S3 버킷의 디렉토리(%s) 내부 데이터를 삭제하는 과정에서 에러가 발생하였습니다.", s3DirectoryPath));
        }
    }
}
