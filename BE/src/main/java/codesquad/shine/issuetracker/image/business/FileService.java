package codesquad.shine.issuetracker.image.business;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
public class FileService {

    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws IOException {
        UUID uuid = UUID.randomUUID();

        // 확장자 추출하기
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

        // 저장 될 이름
        String savedFileName = uuid.toString() + extension;

        // fullUrl
        String uploadFullUrl = uploadPath + "/" + savedFileName;

        // file upload
        FileOutputStream fos = new FileOutputStream(uploadFullUrl);
        fos.write(fileData);
        fos.close();
        return savedFileName;
    }

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
