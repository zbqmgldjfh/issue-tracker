package codesquad.shine.issuetracker.common.vo;

public enum Status {

    OPEN(true),
    CLOSED(false);

    private final boolean bool;

    Status(boolean bool) {
        this.bool = bool;
    }

    public boolean toBoolean() {
        return this.bool;
    }
}
