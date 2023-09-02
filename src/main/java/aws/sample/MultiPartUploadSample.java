package aws.sample;

import aws.s3.mulitpart.PreSigned;
import aws.s3.mulitpart.UploadId;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import software.amazon.awssdk.regions.Region;

public class MultiPartUploadSample {

    public static void main(String[] args) {
        String bucketName = "infrun";
        String objectKey = "test22.mp4";
        String profileName = "InfrunManager";
        Region region = Region.AP_NORTHEAST_2;
        int partSize = 3; //part Size
        Duration duration = Duration.ofHours(1); //PreSignedUrl Expire Time

        /**
         * step 1 - Upload Id 발급
         */
        String uploadId = UploadId.getUploadId(bucketName, objectKey, profileName, region);

        /**
         * step 2 - PreSignedURL 발급
         */
        List<String> preSignedUrlList = new ArrayList<>();
        for (int partNum = 1; partNum <= partSize; partNum++) {
            preSignedUrlList.add(
                PreSigned.getPreSigned(bucketName, objectKey, profileName, region, uploadId,
                    partNum, duration));
        }

        /**
         * step 3 - 프론트로부터 Etag 발급
         */

    }
}
