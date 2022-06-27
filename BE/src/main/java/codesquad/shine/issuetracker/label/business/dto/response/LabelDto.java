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
    private Boolean checked;

    public LabelDto(Label label) {
        this.id = label.getId();
        this.title = label.getTitle();
        this.description = label.getDescription();
        this.backgroundColorCode = label.getColor().getBackgroundColorCode();
        this.fontColorCode = label.getColor().getFontColorCode();
        this.checked = false;
    }

    private LabelDto(Long id, String title, String description, String backgroundColorCode, String fontColorCode, Boolean checked) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.backgroundColorCode = backgroundColorCode;
        this.fontColorCode = fontColorCode;
        this.checked = checked;
    }

    public static LabelDto of(Label label, boolean checked) {
        return new LabelDto(label.getId(), label.getTitle(), label.getDescription(),
                label.getColor().getBackgroundColorCode(), label.getColor().getFontColorCode(), checked);
    }
}
