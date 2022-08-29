package codesquad.shine.issuetracker.milestone.presentation;

import codesquad.shine.issuetracker.auth.annotation.ForLoginUser;
import codesquad.shine.issuetracker.milestone.business.MilestoneService;
import codesquad.shine.issuetracker.milestone.dto.request.MilestoneCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/milestones")
public class MilestoneController {

    private final MilestoneService milestoneService;

    @ForLoginUser
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody MilestoneCreateRequest request) {
        milestoneService.create(request);
    }
}
