package codesquad.shine.issuetracker.milestone.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {
    List<Milestone> findAllByIsOpen(boolean isOpen);
}
