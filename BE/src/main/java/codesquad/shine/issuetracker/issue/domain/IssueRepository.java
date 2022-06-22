package codesquad.shine.issuetracker.issue.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IssueRepository extends JpaRepository<Issue, Long> {

    @Query("select i from Issue i " +
            "join fetch i.author u " +
            "join fetch i.milestone m " +
            "where i.id = :id")
    Optional<Issue> optimizationFindById(@Param("id") Long id);

    @Query(value = "select i from Issue i " +
            "join fetch i.author u " +
            "join fetch i.milestone m " +
            "where i.open = :open",
            countQuery = "select count(i.id) from Issue i where i.open = :open"
    )
    Page<Issue> optimizationFindIssueByStatus(@Param("open") Boolean open, Pageable pageable);
}
