package codesquad.shine.issuetracker.issue.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue, Long>, IssueQueryRepository {
}
