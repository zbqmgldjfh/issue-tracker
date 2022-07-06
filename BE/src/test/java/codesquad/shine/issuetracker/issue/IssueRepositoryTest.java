package codesquad.shine.issuetracker.issue;

import codesquad.shine.issuetracker.issue.business.IssueService;
import codesquad.shine.issuetracker.issue.domain.Issue;
import codesquad.shine.issuetracker.issue.domain.IssueRepository;
import codesquad.shine.issuetracker.issue.presentation.dto.request.AssigneesEditRequest;
import codesquad.shine.issuetracker.user.domain.User;
import codesquad.shine.issuetracker.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class IssueRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    UserRepository userRepository;

    @Autowired
    IssueRepository issueRepository;

    @Autowired
    IssueService issueService;

    @Test
    public void issue_assignee_test() {
        // given
        User user1 = new User("shine1", "shine@naver.com1", "avatarurl");
        User user2 = new User("shine2", "shine@naver.com2", "avatarurl");
        User user3 = new User("shine3", "shine@naver.com3", "avatarurl");
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        Issue issue = new Issue("test", true, user1);
        issue.addAssignees(List.of(user1, user2, user3));
        Issue savedIssue = issueRepository.save(issue);

        System.out.println("savedIssue = " + savedIssue.getId());

        em.flush();
        em.clear();

        // when
        System.out.println("=======[find issue]======");
        Issue findIssue = issueRepository.findById(savedIssue.getId()).get();
        System.out.println("findIssue.getId() = " + findIssue.getId());
        System.out.println("=======[find issue end]======");
        issueService.editAssignees(findIssue.getId(), new AssigneesEditRequest(List.of(user2.getId())));

        // then
        assertThat(findIssue.getIssueAssignees().size()).isEqualTo(1);
        assertThat(findIssue.getIssueAssignees().get(0).getUser()).isEqualTo(user2);
    }
}
