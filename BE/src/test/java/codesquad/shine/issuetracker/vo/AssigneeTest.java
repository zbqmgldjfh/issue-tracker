package codesquad.shine.issuetracker.vo;

import codesquad.shine.issuetracker.common.vo.Assignee;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AssigneeTest {

    @Test
    @DisplayName("VO인 assignee를 만든다.")
    public void create_assignee_test() {
        Assignee assignee = new Assignee(1L, "shine", "avatarUrl", false);
        Assertions.assertThat(assignee).isEqualTo(new Assignee(1L, "shine", "avatarUrl", false));
    }

    @Test
    @DisplayName("1이상의 id값이 들어와야 한다.")
    public void valid_range_id_test() {
        assertThatThrownBy(() -> new Assignee(-1L, "shine", "avatarUrl", false)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Assignee(0L, "shine", "avatarUrl", false)).isInstanceOf(IllegalArgumentException.class);

        Assignee assignee = new Assignee(5L, "shine", "avatarUrl", false);
        Assertions.assertThat(assignee).isEqualTo(new Assignee(5L, "shine", "avatarUrl", false));
    }

    @Test
    @DisplayName("사용자 이름의 길이는 1이상이여야 한다.")
    public void valid_length_name_test() {
        assertThatThrownBy(() -> new Assignee(2L, "", "avatarUrl", false)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Assignee(2L, null, "avatarUrl", false)).isInstanceOf(IllegalArgumentException.class);

        Assignee assignee = new Assignee(5L, "shine", "avatarUrl", false);
        Assertions.assertThat(assignee).isEqualTo(new Assignee(5L, "shine", "avatarUrl", false));
    }
}
