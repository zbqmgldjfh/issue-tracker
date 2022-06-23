package codesquad.shine.issuetracker.image.domain;

import codesquad.shine.issuetracker.common.imbeddable.BaseTimeEntity;
import codesquad.shine.issuetracker.issue.domain.Issue;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;

@Getter
@Entity
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class Image extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    private String imgName;
    private String oriImgName;
    private String imgUrl;

    public Image(Long id, String imgName, String oriImgName, String imgUrl) {
        this.id = id;
        this.imgName = imgName;
        this.oriImgName = oriImgName;
        this.imgUrl = imgUrl;
    }

    @Builder
    public Image(String imgName, String oriImgName, String imgUrl, Issue issue) {
        Assert.hasText(imgName, "title must not be null and must contain at least one non-whitespace character");
        Assert.hasText(oriImgName, "title must not be null and must contain at least one non-whitespace character");
        Assert.hasText(imgUrl, "title must not be null and must contain at least one non-whitespace character");
        this.imgName = imgName;
        this.oriImgName = oriImgName;
        this.imgUrl = imgUrl;
    }

    public void updateItemImg(String oriImgName, String imgName, String imgUrl) {
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }
}
