package ports.driver;

import java.time.LocalDateTime;
import java.util.Objects;

public class MergeRequest {

    public static enum STATUS {OPEN, MERGED} ;
    private STATUS status;
    private final long mergeRequestID;
    private final long projectID;
    private LocalDateTime mergedAt;
    private final LocalDateTime openedAt;

    public long getMergeRequestID() {
        return mergeRequestID;
    }

    public long getProjectID() {
        return projectID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MergeRequest that = (MergeRequest) o;
        return mergeRequestID == that.mergeRequestID && projectID == that.projectID;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setMergedAt(LocalDateTime mergedAt) {
        this.mergedAt = mergedAt;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mergeRequestID, projectID);
    }

    public LocalDateTime getMergedAt() {
        return mergedAt;
    }

    public LocalDateTime getOpenedAt() {
        return openedAt;
    }

    public MergeRequest(final long id, final long projectID, STATUS status, final LocalDateTime openedAt) {

        this.mergeRequestID = id;
        this.projectID = projectID;
        this.status = status;
        this.openedAt = openedAt;
    }
}
