package codesquad.shine.issuetracker.common.vo;

public enum Status {

    OPEN(true),
    CLOSED(false);

    private final boolean bool;

    Status(boolean bool) {
        this.bool = bool;
    }

    public static boolean toBoolean(String status) {
        return Status.valueOf(status.toUpperCase()).bool;
    }
}
