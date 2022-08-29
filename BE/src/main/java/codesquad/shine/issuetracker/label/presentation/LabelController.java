package codesquad.shine.issuetracker.label.presentation;

import codesquad.shine.issuetracker.auth.annotation.ForLoginUser;
import codesquad.shine.issuetracker.label.business.LabelService;
import codesquad.shine.issuetracker.label.dto.reqeust.LabelCreateRequest;
import codesquad.shine.issuetracker.label.dto.reqeust.LabelEditRequest;
import codesquad.shine.issuetracker.label.dto.response.LabelsResponse;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/labels")
public class LabelController {

    private final LabelService labelService;

    @ForLoginUser
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody LabelCreateRequest request) {
        labelService.create(request);
    }

    @GetMapping
    public LabelsResponse searchLabelList() {
        return labelService.findALL();
    }

    @ForLoginUser
    @PatchMapping("/{labelId}")
    public void edit(@PathVariable Long labelId, @RequestBody LabelEditRequest request) {
        labelService.edit(labelId, request);
    }

    @ForLoginUser
    @DeleteMapping("/{labelId}")
    public void delete(@PathVariable Long labelId) {
        labelService.delete(labelId);
    }
}
