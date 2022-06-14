package codesquad.shine.issuetracker.comment.domain;

import codesquad.shine.issuetracker.common.imbeddable.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String description;

}
