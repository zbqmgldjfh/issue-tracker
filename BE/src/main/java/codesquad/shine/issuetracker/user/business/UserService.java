package codesquad.shine.issuetracker.user.business;

import codesquad.shine.issuetracker.common.vo.Assignee;
import codesquad.shine.issuetracker.exception.ErrorCode;
import codesquad.shine.issuetracker.exception.unchecked.NotFoundException;
import codesquad.shine.issuetracker.issue.domain.Issue;
import codesquad.shine.issuetracker.user.domain.User;
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

    public User findUserByEmail(String userEmail) {
        return userRepository.findUserByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    public List<User> getAssigneeInId(List<Long> ids) {
        return userRepository.findAllById(ids);
    }

    public List<User> findAllByIds(List<Long> assigneeIds) {
        return userRepository.findAllById(assigneeIds);
    }

    public List<Assignee> findAllWithCheckAssignee(Issue issue) {
        return userRepository.findAll().stream()
                .map(user -> Assignee.of(user, isInIssue(user, issue)))
                .collect(Collectors.toList());
    }

    private boolean isInIssue(User targetUser, Issue issue) {
        return issue.getIssueAssignees().stream()
                .map(ia -> ia.getUser())
                .filter(user -> user.equals(targetUser))
                .findAny()
                .isPresent();
    }
}
