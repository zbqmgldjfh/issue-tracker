package codesquad.shine.issuetracker.user.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String userName;
    private String email;
    private String imageUrl;

    public User(long id, String userName, String email, String imageUrl) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.imageUrl = imageUrl;
    }
}
