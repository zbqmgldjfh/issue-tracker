package codesquad.shine.issuetracker.image.presentation.dto;

import lombok.Getter;

@Getter
public class ImageResponse {
    private String uploadFullUrl;

    public ImageResponse() {
    }

    public ImageResponse(String uploadFullUrl) {
        this.uploadFullUrl = uploadFullUrl;
    }
}
