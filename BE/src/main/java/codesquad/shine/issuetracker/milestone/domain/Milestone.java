package codesquad.shine.issuetracker.milestone.domain;

import codesquad.shine.issuetracker.common.imbeddable.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Milestone extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "milestone_id")
    private Long id;

    private String title;
    private String description;
    private LocalDateTime dueDate;
    private boolean isOpen;
}
