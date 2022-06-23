package codesquad.shine.issuetracker.image.business;

import codesquad.shine.issuetracker.exception.ErrorCode;
import codesquad.shine.issuetracker.exception.unchecked.NotAvailableException;
import codesquad.shine.issuetracker.image.business.dto.UploadFileResponse;
import codesquad.shine.issuetracker.image.domain.Image;
import codesquad.shine.issuetracker.image.domain.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final FileService fileService;
    private final ImageRepository imageRepository;

    public Image upload(MultipartFile file) throws IOException {

        String originalFilename = file.getOriginalFilename();
        UploadFileResponse uploadFileResponse;

        if (!StringUtils.hasText(originalFilename)) {
            throw new NotAvailableException(ErrorCode.FILE_NAME_NOT_EXIST);
        }
        uploadFileResponse = fileService.uploadFile(originalFilename, file);

        Image newImage = Image.builder()
                .oriImgName(originalFilename)
                .imgName(uploadFileResponse.getSavedFileName())
                .imgUrl(uploadFileResponse.getUploadFullUrl())
                .build();

        return imageRepository.save(newImage);
    }
}
