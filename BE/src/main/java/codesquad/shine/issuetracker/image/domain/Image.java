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

    private String imageName;
    private String originImageName;
    private String imageUrl;

    public Image(Long id, String imageName, String originImageName, String imageUrl) {
        this.id = id;
        this.imageName = imageName;
        this.originImageName = originImageName;
        this.imageUrl = imageUrl;
    }

    @Builder
    public Image(String imageName, String originImageName, String imageUrl, Issue issue) {
        Assert.hasText(imageName, "title must not be null and must contain at least one non-whitespace character");
        Assert.hasText(originImageName, "title must not be null and must contain at least one non-whitespace character");
        Assert.hasText(imageUrl, "title must not be null and must contain at least one non-whitespace character");
        this.imageName = imageName;
        this.originImageName = originImageName;
        this.imageUrl = imageUrl;
    }

    public void updateItemImg(String oriImgName, String imgName, String imgUrl) {
        this.originImageName = oriImgName;
        this.imageName = imgName;
        this.imageUrl = imgUrl;
    }
}
