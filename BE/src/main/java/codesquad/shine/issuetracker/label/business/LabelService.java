package codesquad.shine.issuetracker.label.business;

import codesquad.shine.issuetracker.exception.ErrorCode;
import codesquad.shine.issuetracker.exception.unchecked.NotFoundException;
import codesquad.shine.issuetracker.issue.domain.Issue;
import codesquad.shine.issuetracker.label.business.dto.response.LabelDto;
import codesquad.shine.issuetracker.label.domain.Label;
import codesquad.shine.issuetracker.label.domain.LabelRepository;
import codesquad.shine.issuetracker.label.dto.reqeust.LabelCreateRequest;
import codesquad.shine.issuetracker.label.dto.reqeust.LabelEditRequest;
import codesquad.shine.issuetracker.label.dto.response.LabelEditResponse;
import codesquad.shine.issuetracker.label.dto.response.LabelIdResponse;
import codesquad.shine.issuetracker.label.dto.response.LabelsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class LabelService {

    private final LabelRepository labelRepository;

    public LabelIdResponse create(LabelCreateRequest request) {
        Label label = labelRepository.save(Label.createEntity(request.getTitle(), request.getDescription(), request.getColor()));
        return new LabelIdResponse(label.getId());
    }

    @Transactional(readOnly = true)
    public LabelsResponse findALL() {
        List<LabelDto> labelDtoList = labelRepository.findAll()
                .stream()
                .map(LabelDto::new)
                .collect(Collectors.toList());
        return new LabelsResponse(labelDtoList);
    }

    public LabelEditResponse edit(Long labelId, LabelEditRequest request) {
        Label findLabel = labelRepository.findById(labelId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.LABEL_NOT_FOUND));

        findLabel.editProperties(request.getTitle(), request.getDescription(), request.getColor());
        return LabelEditResponse.builder()
                .title(findLabel.getTitle())
                .description(findLabel.getDescription())
                .color(findLabel.getColor())
                .build();
    }

    public void delete(Long labelId) {
        Label findLabel = labelRepository.findById(labelId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.LABEL_NOT_FOUND));

        findLabel.getIssues()
                .forEach(issue -> issue.detachLabel(findLabel));
    }

    @Transactional(readOnly = true)
    public List<LabelDto> findAllDto() {
        return labelRepository.findAll()
                .stream()
                .map(LabelDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Label> getLabelsInId(List<Long> ids) {
        if (ids == null) {
            return Collections.emptyList();
        }
        return labelRepository.findAllById(ids);
    }

    public Long count() {
        return labelRepository.count();
    }

    public List<LabelDto> findAllWithCheckAssignee(Issue issue) {
        return labelRepository.findAll().stream()
                .map(label -> LabelDto.of(label, isInIssue(label, issue)))
                .collect(Collectors.toList());
    }

    private boolean isInIssue(Label targetLabel, Issue issue) {
        return issue.getLabels().stream()
                .filter(label -> label.equals(targetLabel))
                .findAny()
                .isPresent();
    }
}
