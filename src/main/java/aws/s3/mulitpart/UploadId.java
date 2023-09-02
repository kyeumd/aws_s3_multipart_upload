package aws.s3.mulitpart;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateMultipartUploadRequest;
import software.amazon.awssdk.services.s3.model.CreateMultipartUploadResponse;

public class UploadId {

    public static String getUploadId(String bucketName, String objectKey, String profileName,
        Region region) {
//        String bucketName = "infrun";
//        String objectKey = "test.mp4";
//        String profileName = "InfrunManager";
//        Region region = Region.AP_NORTHEAST_2;
        ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create(
            profileName);

        S3Client s3 = S3Client.builder()
            .region(region)
            .credentialsProvider(credentialsProvider)
            .build();

        CreateMultipartUploadRequest createRequest = CreateMultipartUploadRequest.builder()
            .bucket(bucketName)
            .key(objectKey)
            .build();

        CreateMultipartUploadResponse createResponse = s3.createMultipartUpload(createRequest);
        String uploadId = createResponse.uploadId();
        System.out.println("Upload ID: " + uploadId);

        return uploadId;
    }
}
