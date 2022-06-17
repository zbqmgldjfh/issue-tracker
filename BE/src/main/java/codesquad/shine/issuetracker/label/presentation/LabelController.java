package codesquad.shine.issuetracker.label.presentation;

import codesquad.shine.issuetracker.label.business.LabelService;
import codesquad.shine.issuetracker.label.dto.reqeust.LabelCreateRequest;
import codesquad.shine.issuetracker.label.dto.reqeust.LabelEditRequest;
import codesquad.shine.issuetracker.label.dto.response.LabelListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/labels")
public class LabelController {

    private final LabelService labelService;

    @PostMapping
    public void create(@RequestBody LabelCreateRequest request) {
        labelService.create(request.getTitle(), request.getDescription(), request.getColor());
    }

    @GetMapping
    public LabelListResponse searchLabelList() {
        return labelService.findALL();
    }

    @PatchMapping("/{labelId}")
    public void edit(@PathVariable Long labelId, @RequestBody LabelEditRequest request) {
        labelService.edit(labelId, request);
    }

    @DeleteMapping("/{labelId}")
    public void delete(@PathVariable Long labelId) {
        labelService.delete(labelId);
    }
}
