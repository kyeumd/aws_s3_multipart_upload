package aws.s3.auth;

import java.net.URI;
import java.net.URISyntaxException;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import uk.co.lucasweb.aws.v4.signer.HttpRequest;
import uk.co.lucasweb.aws.v4.signer.Signer;
import uk.co.lucasweb.aws.v4.signer.credentials.AwsCredentials;

public class AWSV4Creator {

    public static void main(String[] args) throws URISyntaxException {
        String uploadId = "EJskR6EZJMDFMVH43O2zIWkZmO7zYbfQlAPgmlTShXjEjLjuNhivfL8g1rE13L.tZCxkXgtg7QWO9LnWQlwHu7oWxlMrRzQxdc79CcOvo7U1doX03udFSp_ktnc4GWgIhVNcw8m2wcqn.oJPjS5DFw--";
        String dateTime = "20230831T044813Z";

        String result = returnAuthV4(uploadId, dateTime);

        System.out.println(result);

    }

    private static String returnAuth4UpId(String dateTime) throws URISyntaxException {
        String contentSha256 = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";

        String urI = "https://infrun.s3.ap-northeast-2.amazonaws.com/test/testVideo.mp4?uploads";
        HttpRequest request = new HttpRequest("POST", new URI(urI));

        String region = "ap-northeast-2";
        String host = "infrun.s3.ap-northeast-2.amazonaws.com";
        ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create(
            "InfrunManager");
        software.amazon.awssdk.auth.credentials.AwsCredentials awsCredentials = credentialsProvider.resolveCredentials();

        return Signer.builder()
            .awsCredentials(
                new AwsCredentials(awsCredentials.accessKeyId(),
                    awsCredentials.secretAccessKey()))
            .header("Host", host)
            .header("x-amz-date", dateTime)
            .header("x-amz-content-sha256", contentSha256)
            .region(region)
            .buildS3(request, contentSha256)
            .getSignature();
    }

    private static String returnAuthV4(String uploadId, String dateTime) throws URISyntaxException {
        String contentSha256 = "78f910402814ed956e94f86da40998262e00d18fec50c61a386fea2086723b31";

        String urI =
            "https://infrun.s3.ap-northeast-2.amazonaws.com/test/testVideo.mp4"
                + "?partNumber=1"
                + "&uploadId=" + uploadId;
        System.out.println(urI);
        HttpRequest request = new HttpRequest("PUT", new URI(urI));

        String region = "ap-northeast-2";
        String host = "infrun.s3.ap-northeast-2.amazonaws.com";

        ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create(
            "InfrunManager");
        software.amazon.awssdk.auth.credentials.AwsCredentials awsCredentials = credentialsProvider.resolveCredentials();

        return Signer.builder()
            .awsCredentials(
                new AwsCredentials(awsCredentials.accessKeyId(),
                    awsCredentials.secretAccessKey()))
            .region(region)
            .header("Host", host)
            .header("x-amz-date", dateTime)
            .header("x-amz-content-sha256", contentSha256)
            .buildS3(request, contentSha256)
            .getSignature();
    }
}
