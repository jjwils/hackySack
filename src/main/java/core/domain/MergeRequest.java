package core.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class MergeRequest {

    private MergeRequestStatus status;
    private final long id;
    private final long projectID;
    private LocalDateTime mergedAt;
    private final LocalDateTime openedAt;

    public MergeRequest(final long id, final long projectID, MergeRequestStatus status, final LocalDateTime openedAt) {

        this.id = id;
        this.projectID = projectID;
        this.status = status;
        this.openedAt = openedAt;
    }

    public long getId() {
        return id;
    }

    public MergeRequestStatus status() {
        return status;
    }

    public void setMergedAt(LocalDateTime mergedAt) {
        this.mergedAt = mergedAt;
    }

    public LocalDateTime mergedAt() {
        return mergedAt;
    }

    public LocalDateTime openedAt() {
        return openedAt;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, projectID);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MergeRequest that = (MergeRequest) o;
        return id == that.id && projectID == that.projectID;
    }
}
