package codesquad.shine.issuetracker.issue.domain;

import java.util.Optional;

public class IssueQueryRepositoryImp implements IssueQueryRepository {

    @Override
    public Optional<Issue> optimizationFindById(Long id) {
        return Optional.empty();
    }
}
