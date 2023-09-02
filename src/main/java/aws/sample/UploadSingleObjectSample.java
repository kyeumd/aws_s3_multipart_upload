package aws.sample;

import aws.s3.UploadSingleObject;
import software.amazon.awssdk.regions.Region;

public class UploadSingleObjectSample {

    public static void main(String[] args) {
        String bucketName = "infrun";
        String objectKey = "test.txt";
        String objectPath = "D:\\Documents\\test\\test.txt";
        String profileName = "InfrunManager";
        Region region = Region.AP_NORTHEAST_2;

        UploadSingleObject.uploadObject(bucketName, objectKey, objectPath, profileName, region);
    }
}
