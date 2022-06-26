package codesquad.shine.issuetracker.issue.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IssueQueryRepository {

    @Query("select i from Issue i " +
            "join fetch i.author u " +
            "join fetch i.milestone m " +
            "where i.id = :id")
    Optional<Issue> optimizationFindById(@Param("id") Long id);
}
