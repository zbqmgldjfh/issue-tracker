package codesquad.shine.issuetracker.label.presentation;

import codesquad.shine.issuetracker.label.business.LabelService;
import codesquad.shine.issuetracker.label.dto.reqeust.LabelCreateRequest;
import codesquad.shine.issuetracker.label.dto.reqeust.LabelEditRequest;
import codesquad.shine.issuetracker.label.dto.response.LabelEditResponse;
import codesquad.shine.issuetracker.label.dto.response.LabelIdResponse;
import codesquad.shine.issuetracker.label.dto.response.LabelsResponse;
import codesquad.shine.support.auth.authorization.secured.Secured;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/labels")
public class LabelController {

    private final LabelService labelService;

    @Secured("ROLE_MEMBER")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LabelIdResponse create(@RequestBody LabelCreateRequest request) {
        return labelService.create(request);
    }

    @GetMapping
    public LabelsResponse searchLabelList() {
        return labelService.findALL();
    }

    @Secured("ROLE_MEMBER")
    @PatchMapping("/{labelId}")
    public LabelEditResponse edit(@PathVariable Long labelId, @RequestBody LabelEditRequest request) {
        return labelService.edit(labelId, request);
    }

    @Secured("ROLE_MEMBER")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{labelId}")
    public void delete(@PathVariable Long labelId) {
        labelService.delete(labelId);
    }
}
