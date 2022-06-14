package codesquad.shine.issuetracker.label.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Color {

    private String backgroundColorCode;
    private String fontColorCode;
}
