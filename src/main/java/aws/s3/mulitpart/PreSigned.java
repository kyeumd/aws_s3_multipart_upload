package aws.s3.mulitpart;

import java.time.Duration;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.UploadPartRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedUploadPartRequest;
import software.amazon.awssdk.services.s3.presigner.model.UploadPartPresignRequest;

public class PreSigned {

    public static String getPreSigned(String bucketName, String objectKey,
        String profileName, Region region, String uploadId, int partNum, Duration duration) {

//        String bucketName = "infrun";
//        String keyName = "test.mp4";
//        Region region = Region.AP_NORTHEAST_2;
//        String uploadId = "4oYjec8Igh83FvasqiFE.9I8rxgi_sqky_T7GcSXTFVHe0Dd4fRPHdyDnn8uQ6vVBvrd9VQ0MURKXYFa8asLGBnpUDtRGI5C_yHDzFUBMI0qJkz0r0Hgi0gqpn04VLomNgzjqYfijGhGoS6rVN.W1Q--";

        ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create(
            profileName);

        S3Presigner presigner = S3Presigner.builder()
            .region(region)
            .credentialsProvider(credentialsProvider)
            .build();

        UploadPartRequest uploadPartRequest = UploadPartRequest.builder()
            .bucket(bucketName)
            .uploadId(uploadId)
            .key(objectKey)
            .partNumber(partNum)
            .build();

        UploadPartPresignRequest preSignedRequest = UploadPartPresignRequest.builder()
            .signatureDuration(duration)
            .uploadPartRequest(uploadPartRequest)
            .build();

        PresignedUploadPartRequest preSignedUploadPartRequest = presigner.presignUploadPart(
            preSignedRequest);
        String resultURL = preSignedUploadPartRequest.url().toString();

        System.out.println("Presigned URL to upload a file to: " + resultURL);
        System.out.println("Which HTTP method needs to be used when uploading a file: "
            + preSignedUploadPartRequest.httpRequest().method());

        // Upload content to the Amazon S3 bucket by using this URL.
//        URL url = preSignedUploadPartRequest.url();

        // Create the connection and use it to upload the new object by using the preSigned URL.
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.setDoOutput(true);
//        connection.setRequestProperty("x-amz-meta-author", "dong-kyeum");
//        connection.setRequestMethod("PUT");
//        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
//        out.write("This text was uploaded as an object by using a presigned URL.");
//        out.close();
//
//        connection.getResponseCode();
//        System.out.println("HTTP response code is " + connection.getResponseCode());\

        return resultURL;
    }
}
