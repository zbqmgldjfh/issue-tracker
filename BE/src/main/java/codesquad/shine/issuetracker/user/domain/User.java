package codesquad.shine.issuetracker.user.domain;

import codesquad.shine.issuetracker.auth.OAuthUser;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String userName;
    private String email;
    private String password;
    private String avatarUrl;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "MEMBER_ROLE",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "ROLE")
    private List<String> roles = new ArrayList<>();

    public User(String userName, String email, String avatarUrl) {
        this.userName = userName;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.roles = List.of(RoleType.ROLE_MEMBER.name());
    }

    public User(Long id, String userName, String email, String avatarUrl) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.roles = List.of(RoleType.ROLE_MEMBER.name());
    }

    public User(String userName, String email, String password, String avatarUrl) {
        this(userName, email, password, avatarUrl, List.of(RoleType.ROLE_MEMBER.name()));
    }

    public User(String userName, String email, String password, String avatarUrl, List<String> roles) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.avatarUrl = avatarUrl;
        this.roles = roles;
    }

    public static User of(OAuthUser user) {
        return User.builder()
                .userName(user.getName())
                .email(user.getEmail())
                .avatarUrl(user.getAvatarUrl())
                .build();
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public List<String> getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                '}';
    }
}
