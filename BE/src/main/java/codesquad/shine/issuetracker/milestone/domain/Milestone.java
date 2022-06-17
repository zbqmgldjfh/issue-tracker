package codesquad.shine.issuetracker.milestone.domain;

import codesquad.shine.issuetracker.common.imbeddable.BaseTimeEntity;
import codesquad.shine.issuetracker.issue.domain.Issue;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Entity
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Milestone extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "milestone_id")
    private Long id;

    private String title;
    private String description;
    private LocalDateTime dueDate;
    private boolean isOpen;

    @OneToMany(mappedBy = "milestone")
    private List<Issue> issueList = new ArrayList<>();
}
