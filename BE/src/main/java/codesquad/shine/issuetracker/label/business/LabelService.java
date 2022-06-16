package codesquad.shine.issuetracker.label.business;

import codesquad.shine.issuetracker.exception.ErrorCode;
import codesquad.shine.issuetracker.exception.unchecked.NotFoundException;
import codesquad.shine.issuetracker.label.business.dto.response.LabelDto;
import codesquad.shine.issuetracker.label.domain.Color;
import codesquad.shine.issuetracker.label.domain.Label;
import codesquad.shine.issuetracker.label.domain.LabelRepository;
import codesquad.shine.issuetracker.label.dto.reqeust.LabelEditRequest;
import codesquad.shine.issuetracker.label.dto.response.LabelEditResponse;
import codesquad.shine.issuetracker.label.dto.response.LabelListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class LabelService {

    private final LabelRepository labelRepository;

    public Long create(String title, String description, Color color) {
        Label label = labelRepository.save(Label.createEntity(title, description, color));
        return label.getId();
    }

    @Transactional(readOnly = true)
    public LabelListResponse findALL() {
        List<LabelDto> labelDtoList = labelRepository.findAll()
                .stream()
                .map(LabelDto::new)
                .collect(Collectors.toList());
        return new LabelListResponse(labelDtoList);
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
}
