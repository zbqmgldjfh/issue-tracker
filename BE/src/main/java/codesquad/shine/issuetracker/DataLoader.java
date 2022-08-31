package codesquad.shine.issuetracker;

import codesquad.shine.issuetracker.user.domain.RoleType;
import codesquad.shine.issuetracker.user.domain.User;
import codesquad.shine.issuetracker.user.domain.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader {
    private UserRepository userRepository;

    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void loadData() {
        userRepository.save(new User("admin", "admin@email.com", "password", "url", Arrays.asList(RoleType.ROLE_ADMIN.name())));
        userRepository.save(new User("member", "member@email.com", "password", "url", Arrays.asList(RoleType.ROLE_MEMBER.name())));
    }
}
