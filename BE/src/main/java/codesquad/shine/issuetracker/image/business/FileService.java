package codesquad.shine.issuetracker.image.business;

import codesquad.shine.issuetracker.exception.ErrorCode;
import codesquad.shine.issuetracker.exception.unchecked.FileUploadFailedException;
import codesquad.shine.issuetracker.image.business.dto.UploadFileResponse;
import codesquad.shine.issuetracker.image.infra.S3Properties;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    private final S3Properties properties;
    private final AmazonS3Client amazonS3Client;

    public UploadFileResponse uploadFile(String originalFileName, MultipartFile file) throws IOException {
        String bucketName = properties.getBucket();

        // 확장자 추출하기
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

        // 저장 될 이름
        String savedFileName = UUID.randomUUID().toString() + extension;

        // Meta 데이터 생성
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());

        // file upload
        try (InputStream inputStream = file.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucketName, savedFileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new FileUploadFailedException(ErrorCode.FILE_CANT_UPLOAD);
        }

        String uploadFullUrl = amazonS3Client.getUrl(bucketName, savedFileName).toString();
        return new UploadFileResponse(savedFileName, uploadFullUrl);
    }

    // TODO 미완성
    public void deleteFile(String filePath) throws Exception {
        File deleteFile = new File(filePath);

        if (deleteFile.exists()) {
            deleteFile.delete();
            log.info("파일을 정상적으로 삭제하였습니다.");
            return;
        }
        log.info("파일이 존재하지 않습니다.");
    }
}
