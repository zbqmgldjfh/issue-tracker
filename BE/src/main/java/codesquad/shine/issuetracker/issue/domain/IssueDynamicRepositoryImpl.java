package codesquad.shine.issuetracker.issue.domain;

import codesquad.shine.issuetracker.issue.presentation.dto.request.SearchConditionRequest;
import codesquad.shine.issuetracker.user.domain.User;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;

import static codesquad.shine.issuetracker.comment.domain.QComment.comment;
import static codesquad.shine.issuetracker.issue.domain.QIssue.issue;
import static codesquad.shine.issuetracker.issue.domain.QIssueAssignee.issueAssignee;
import static codesquad.shine.issuetracker.label.domain.QLabel.label;
import static codesquad.shine.issuetracker.milestone.domain.QMilestone.milestone;
import static codesquad.shine.issuetracker.user.domain.QUser.user;

public class IssueDynamicRepositoryImpl implements IssueDynamicRepository {

    private final JPAQueryFactory queryFactory;

    public IssueDynamicRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<Issue> searchIssueByCondition(User loginUser, SearchConditionRequest condition, Pageable pageable) {
        QueryResults<Issue> queryResults = queryFactory.selectFrom(issue)
                .distinct()
                .leftJoin(issue.author, user).fetchJoin()
                .leftJoin(issue.comments, comment)
                .leftJoin(issue.milestone, milestone).fetchJoin()
                .leftJoin(issue.labels, label)
                .leftJoin(issue.issueAssignees, issueAssignee)
                .leftJoin(issueAssignee.user, user)
                .where(
                        isContainQuery(condition.getQuery()),
                        isOpen(condition.getOpen()),
                        isClose(condition.getClose()),
                        isOwner(condition.getOwner(), loginUser),
                        assigneeEquals(condition.getAssignee(), loginUser),
                        isContainComment(condition.getComment(), loginUser)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return PageableExecutionUtils.getPage(queryResults.getResults(), pageable, () -> queryResults.getTotal());
    }

    private BooleanExpression isContainQuery(String query) {
        if (StringUtils.hasText(query)) {
            return issue.title.contains(query);
        }
        return null;
    }

    private BooleanExpression isOpen(Boolean open) {
        return open != null ? issue.open.eq(open) : null;
    }

    private BooleanExpression isClose(Boolean close) {
        return close != null ? issue.open.ne(close) : null;
    }

    private BooleanExpression isOwner(Boolean owner, User loginUser) {
        if (owner != null && owner.equals(true)) {
            return issue.author.eq(loginUser);
        }
        return null;
    }

    private BooleanExpression assigneeEquals(Boolean assignee, User loginUser) {
        if (assignee != null && assignee.equals(true)) {
            return user.eq(loginUser);
        }
        return null;
    }

    private BooleanExpression isContainComment(Boolean isMyComment, User loginUser) {
        if (isMyComment != null && isMyComment.equals(true)) {
            return comment.user.eq(loginUser);
        }
        return null;
    }
}
