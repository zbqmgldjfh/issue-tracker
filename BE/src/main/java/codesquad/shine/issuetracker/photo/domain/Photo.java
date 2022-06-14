package codesquad.shine.issuetracker.photo.domain;

import codesquad.shine.issuetracker.common.imbeddable.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Photo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id")
    private Long id;

    private String imgName;
    private String oriImgName;
    private String imgUrl;

    public Photo(String imgName, String oriImgName, String imgUrl) {
        this.imgName = imgName;
        this.oriImgName = oriImgName;
        this.imgUrl = imgUrl;
    }
}
