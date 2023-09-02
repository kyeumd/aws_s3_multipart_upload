package aws.s3.mulitpart;

import java.util.ArrayList;
import java.util.List;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CompleteMultipartUploadRequest;
import software.amazon.awssdk.services.s3.model.CompletedMultipartUpload;
import software.amazon.awssdk.services.s3.model.CompletedPart;

public class CompleteUpload {

    public static void CompleteUpload(String bucketName, String objectKey, String profileName,
        Region region, String uploadId, List<String> etag) {

//        String bucketName = "infrun";
//        String key = "test.mp4";
//        Region region = Region.AP_NORTHEAST_2;
//        String uploadId = "4oYjec8Igh83FvasqiFE.9I8rxgi_sqky_T7GcSXTFVHe0Dd4fRPHdyDnn8uQ6vVBvrd9VQ0MURKXYFa8asLGBnpUDtRGI5C_yHDzFUBMI0qJkz0r0Hgi0gqpn04VLomNgzjqYfijGhGoS6rVN.W1Q--"; // Multipart Upload의 업로드 ID

        ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create(
            profileName);

        S3Client s3 = S3Client.builder()
            .region(region)
            .credentialsProvider(credentialsProvider)
            .build();

//        String etag1 = "c6462a92be8d7d1f6868d7338d8c5ad4";
//        String etag2 = "9892cdd487e4da8d0e3e0c2902f8cf92";
//        String etag3 = "de951bd3aef1f409316e7abac9edd140";
        List<CompletedPart> partList = new ArrayList<>();
        for (int i = 1; i <= etag.size(); i++) {
            partList.add(CompletedPart.builder().partNumber(i).eTag(etag.get(i - 1)).build());
        }

        // Finally call completeMultipartUpload operation to tell S3 to merge all uploaded
        // parts and finish the multipart operation.
        CompletedMultipartUpload completedMultipartUpload = CompletedMultipartUpload.builder()
            .parts(partList)
            .build();

        CompleteMultipartUploadRequest completeMultipartUploadRequest =
            CompleteMultipartUploadRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .uploadId(uploadId)
                .multipartUpload(completedMultipartUpload)
                .build();

        s3.completeMultipartUpload(completeMultipartUploadRequest);
    }
}
