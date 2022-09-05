package codesquad.shine.issuetracker.milestone.presentation;

import codesquad.shine.issuetracker.common.vo.Status;
import codesquad.shine.issuetracker.milestone.business.MilestoneService;
import codesquad.shine.issuetracker.milestone.dto.request.MilestoneCreateRequest;
import codesquad.shine.issuetracker.milestone.dto.request.MilestoneEditRequest;
import codesquad.shine.issuetracker.milestone.dto.response.MilestoneEditResponse;
import codesquad.shine.issuetracker.milestone.dto.response.MilestoneListResponse;
import codesquad.shine.support.auth.authorization.secured.Secured;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/milestones")
public class MilestoneController {

    private final MilestoneService milestoneService;

    @Secured("ROLE_MEMBER")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void create(@RequestBody MilestoneCreateRequest request) {
        milestoneService.create(request);
    }

    @GetMapping
    public MilestoneListResponse search(@RequestParam Status status) {
        System.out.println("status = " + status.toBoolean());
        return milestoneService.findAllByStatus(status);
    }

    @Secured("ROLE_MEMBER")
    @PatchMapping("/{milestoneId}")
    public MilestoneEditResponse edit(@PathVariable Long milestoneId, @RequestBody MilestoneEditRequest request) {
        return milestoneService.edit(milestoneId, request);
    }

    @Secured("ROLE_MEMBER")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{milestoneId}")
    public void delete(@PathVariable Long milestoneId) {
        milestoneService.delete(milestoneId);
    }
}
