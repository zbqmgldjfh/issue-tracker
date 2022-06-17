package codesquad.shine.issuetracker.label.presentation;

import codesquad.shine.issuetracker.auth.annotation.ForLoginUser;
import codesquad.shine.issuetracker.label.business.LabelService;
import codesquad.shine.issuetracker.label.dto.reqeust.LabelCreateRequest;
import codesquad.shine.issuetracker.label.dto.reqeust.LabelEditRequest;
import codesquad.shine.issuetracker.label.dto.response.LabelListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/labels")
public class LabelController {

    private final LabelService labelService;

    @ForLoginUser
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody LabelCreateRequest request) {
        labelService.create(request);
    }

    @GetMapping
    public LabelListResponse searchLabelList() {
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
