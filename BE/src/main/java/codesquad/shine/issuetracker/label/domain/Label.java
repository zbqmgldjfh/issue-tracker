package codesquad.shine.issuetracker.label.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "label_id")
    private Long id;

    private String title;
    private String description;

    public Label(String title, String description, Color color) {
        this.title = title;
        this.description = description;
        this.color = color;
    }

    @Embedded
    private Color color;
}
