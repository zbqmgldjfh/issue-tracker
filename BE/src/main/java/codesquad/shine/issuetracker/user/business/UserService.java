package codesquad.shine.issuetracker.user.business;

import codesquad.shine.issuetracker.common.vo.Assignee;
import codesquad.shine.issuetracker.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<Assignee> getAllAssignee() {
        return userRepository.findAll().stream()
                .map(u -> new Assignee(u.getId(), u.getUserName(), u.getAvatarUrl(), false))
                .collect(Collectors.toList());
    }
}
