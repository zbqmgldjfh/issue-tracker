package codesquad.shine.issuetracker.image.domain;

import codesquad.shine.issuetracker.common.imbeddable.BaseTimeEntity;
import codesquad.shine.issuetracker.issue.domain.Issue;
import lombok.*;
import org.springframework.util.Assert;

import javax.persistence.*;

@Getter
@Entity
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    private String imgName;
    private String oriImgName;
    private String imgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_id")
    private Issue issue;

    public Image(Long id, String imgName, String oriImgName, String imgUrl, Issue issue) {
        this.id = id;
        this.imgName = imgName;
        this.oriImgName = oriImgName;
        this.imgUrl = imgUrl;
        this.issue = issue;
    }

    @Builder
    public Image(String imgName, String oriImgName, String imgUrl, Issue issue) {
        Assert.hasText(imgName, "title must not be null and must contain at least one non-whitespace character");
        Assert.hasText(oriImgName, "title must not be null and must contain at least one non-whitespace character");
        Assert.hasText(imgUrl, "title must not be null and must contain at least one non-whitespace character");
        Assert.notNull(issue, "issue must not be null");
        this.imgName = imgName;
        this.oriImgName = oriImgName;
        this.imgUrl = imgUrl;
        this.issue = issue;
    }
}
