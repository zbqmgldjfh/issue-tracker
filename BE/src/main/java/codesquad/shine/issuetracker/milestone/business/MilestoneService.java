package codesquad.shine.issuetracker.milestone.business;

import codesquad.shine.issuetracker.exception.ErrorCode;
import codesquad.shine.issuetracker.exception.unchecked.NotFoundException;
import codesquad.shine.issuetracker.milestone.business.dto.response.MilestoneDto;
import codesquad.shine.issuetracker.milestone.domain.Milestone;
import codesquad.shine.issuetracker.milestone.domain.MilestoneRepository;
import codesquad.shine.issuetracker.milestone.dto.request.MilestoneCreateRequest;
import codesquad.shine.issuetracker.milestone.dto.request.MilestoneEditRequest;
import codesquad.shine.issuetracker.milestone.dto.response.MilestoneEditResponse;
import codesquad.shine.issuetracker.milestone.dto.response.MilestoneListResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class MilestoneService {

    private MilestoneRepository milestoneRepository;

    public Long create(MilestoneCreateRequest request) {
        Milestone milestone = Milestone.of(request);
        Milestone saved = milestoneRepository.save(milestone);
        return saved.getId();
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
                .map(MilestoneDto::new)
                .collect(Collectors.toList());
        return new MilestoneListResponse(milestoneDtoList);
    }
}