package codesquad.shine.issuetracker.issue.domain;

import codesquad.shine.issuetracker.issue.presentation.dto.request.SearchConditionRequest;
import codesquad.shine.issuetracker.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IssueDynamicRepository {
    Page<Issue> searchIssueByCondition(User user, SearchConditionRequest condition, Pageable pageable);
}
