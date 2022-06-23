package codesquad.shine.issuetracker.image.business.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UploadFileResponse {
    private final String savedFileName;
    private final String uploadFullUrl;
}
