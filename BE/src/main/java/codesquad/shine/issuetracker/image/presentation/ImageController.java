package codesquad.shine.issuetracker.image.presentation;

import codesquad.shine.issuetracker.auth.annotation.ForLoginUser;
import codesquad.shine.issuetracker.image.business.ImageService;
import codesquad.shine.issuetracker.image.domain.Image;
import codesquad.shine.issuetracker.image.presentation.dto.ImageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    @ForLoginUser
    @PostMapping
    public ImageResponse upload(@RequestParam("file") MultipartFile file) throws IOException {
        Image savedImg = imageService.upload(file);
        return new ImageResponse(savedImg.getImgUrl());
    }
}
