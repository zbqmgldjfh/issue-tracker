package codesquad.shine.issuetracker.milestone.business;

import codesquad.shine.issuetracker.common.vo.Status;
import codesquad.shine.issuetracker.exception.ErrorCode;
import codesquad.shine.issuetracker.exception.unchecked.NotFoundException;
import codesquad.shine.issuetracker.issue.domain.Issue;
import codesquad.shine.issuetracker.milestone.business.dto.MilestoneDto;
import codesquad.shine.issuetracker.milestone.domain.Milestone;
import codesquad.shine.issuetracker.milestone.domain.MilestoneRepository;
import codesquad.shine.issuetracker.milestone.dto.request.MilestoneCreateRequest;
import codesquad.shine.issuetracker.milestone.dto.request.MilestoneEditRequest;
import codesquad.shine.issuetracker.milestone.dto.response.MilestoneEditResponse;
import codesquad.shine.issuetracker.milestone.dto.response.MilestoneIdResponse;
import codesquad.shine.issuetracker.milestone.dto.response.MilestoneListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class MilestoneService {

    private final MilestoneRepository milestoneRepository;

    public MilestoneIdResponse create(MilestoneCreateRequest request) {
        Milestone milestone = Milestone.of(request);
        Milestone saved = milestoneRepository.save(milestone);
        return new MilestoneIdResponse(saved.getId());
    }

    public void delete(Long milestoneId) {
        Milestone findMilestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.LABEL_NOT_FOUND));

        milestoneRepository.delete(findMilestone);
    }

    public MilestoneEditResponse edit(long milestoneId, MilestoneEditRequest request) {
        Milestone findMilestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.MILESTONE_NOT_FOUND));

        findMilestone.editProperties(request.getTitle(), request.getDescription(), request.getDueDate());
        return MilestoneEditResponse.builder()
                .title(findMilestone.getTitle())
                .description(findMilestone.getDescription())
                .dueDate(findMilestone.getDueDate())
                .build();
    }

    public MilestoneListResponse findALL() {
        List<MilestoneDto> milestoneDtoList = milestoneRepository.findAll().stream()
                .map(m -> MilestoneDto.of(m, false))
                .collect(Collectors.toList());
        return new MilestoneListResponse(milestoneDtoList);
    }

    public List<MilestoneDto> findAllDto() {
        return milestoneRepository.findAll().stream()
                .map(m -> MilestoneDto.of(m, false))
                .collect(Collectors.toList());
    }

    public Milestone findById(Long milestoneId) {
        if (milestoneId == null) {
            return null;
        }
        return milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.MILESTONE_NOT_FOUND));
    }

    public Long count() {
        return milestoneRepository.count();
    }

    public List<MilestoneDto> findAllWithCheckAssignee(Issue issue) {
        return milestoneRepository.findAll().stream()
                .map(milestone -> MilestoneDto.of(milestone, isAssigneeMilestone(milestone, issue)))
                .collect(Collectors.toList());
    }

    private Boolean isAssigneeMilestone(Milestone milestone, Issue issue) {
        if (issue.getMilestone() == null) {
            return false;
        }
        return issue.getMilestone().equals(milestone);
    }

    public MilestoneListResponse findAllByStatus(Status status) {
        List<MilestoneDto> milestoneDtoList = milestoneRepository.findAllByIsOpen(status.toBoolean())
                .stream()
                .map(MilestoneDto::of)
                .collect(Collectors.toList());
        return new MilestoneListResponse(milestoneDtoList);
    }
}
