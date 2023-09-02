package aws.s3;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

public class UploadSingleObject {

    public static void uploadObject(String bucketName, String objectKey, String objectPath,
        String profileName, Region region) {

        /**
         * example
         */
//        String bucketName = "infrun";
//        String objectKey = "json\\SAN+V75-varo-via.json";
//        String objectPath = "D:\\Downloads\\SAN+V75-varo-via.json";
//        String profileName = "InfrunManager";
//        Region region = Region.AP_NORTHEAST_2;

        /**
         *  ProfileCredentialsProvider or this
         */
//        String accessKey = "accessKey";
//        String secretKey = "secretKey";
//        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

        ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create(
            profileName);

        S3Client s3 = S3Client.builder()
            .region(region)
            .credentialsProvider(credentialsProvider)
            .build();

        putS3Object(s3, bucketName, objectKey, objectPath);
        s3.close();
    }

    private static void putS3Object(S3Client s3, String bucketName, String objectKey,
        String objectPath) {
        try {
            Map<String, String> metadata = new HashMap<>();
            metadata.put("x-amz-meta-myVal", "test");
            PutObjectRequest putOb = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .metadata(metadata)
                .build();

            s3.putObject(putOb, RequestBody.fromFile(new File(objectPath)));
            System.out.println("Successfully placed " + objectKey + " into bucket " + bucketName);

        } catch (S3Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

}
