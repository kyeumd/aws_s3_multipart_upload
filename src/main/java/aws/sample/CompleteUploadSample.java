package aws.sample;

import aws.s3.mulitpart.CompleteUpload;
import java.util.List;
import software.amazon.awssdk.regions.Region;

public class CompleteUploadSample {

    public static void main(String[] args) {
        String bucketName = "infrun";
        String objectKey = "test22.mp4";
        String profileName = "InfrunManager";
        Region region = Region.AP_NORTHEAST_2;
        //input Your upload-id
        String uploadIdSample = "gRDYZ7a6ICaVJ4nbuMbMZ_DQIH_g7WG9d4Ktnm2miRyx0PPTHXjGgxgZb0D85jpk2TF5kIn9YEQ1CONTM6GrKzXA2cUOa_CbrtM1crYCq4csaJHuhUxacMoqnSw9hXQH02_XJOSn71bXxRp7Iygcyw--";
        //input Etag List
        List<String> listEtag = List.of(
            "c6462a92be8d7d1f6868d7338d8c5ad4",
            "9892cdd487e4da8d0e3e0c2902f8cf92",
            "de951bd3aef1f409316e7abac9edd140"
        );
        CompleteUpload.CompleteUpload(bucketName, objectKey, profileName, region, uploadIdSample,
            listEtag);
    }
}
