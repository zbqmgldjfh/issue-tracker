package codesquad.shine.issuetracker.user.domain;

import codesquad.shine.issuetracker.auth.OAuthUser;
import codesquad.shine.issuetracker.issue.domain.IssueAssignee;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Entity
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String userName;
    private String email;
    private String avatarUrl;

    @OneToMany(mappedBy = "issue")
    private List<IssueAssignee> issueAssignees = new ArrayList<>();

    public User(String userName, String email, String avatarUrl) {
        this.userName = userName;
        this.email = email;
        this.avatarUrl = avatarUrl;
    }

    public static User of(OAuthUser user) {
        return User.builder()
                .userName(user.getName())
                .email(user.getEmail())
                .avatarUrl(user.getAvatarUrl())
                .build();
    }
}
