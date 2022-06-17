package codesquad.shine.issuetracker.label.business.dto.response;

import codesquad.shine.issuetracker.label.domain.Label;
import lombok.Getter;

@Getter
public class LabelDto {
    private Long id;
    private String title;
    private String description;
    private String backgroundColorCode;
    private String fontColorCode;

    public LabelDto(Label label) {
        this.id = label.getId();
        this.title = label.getTitle();
        this.description = label.getDescription();
        this.backgroundColorCode = label.getColor().getBackgroundColorCode();
        this.fontColorCode = label.getColor().getFontColorCode();
    }
}
