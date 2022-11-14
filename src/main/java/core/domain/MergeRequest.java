package core.domain;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class MergeRequest {

    private MergeRequestStatus status;
    private final long id;
    private final long projectID;
    private LocalDateTime mergedAt;
    private final LocalDateTime openedAt;
    private final LocalDateTime firstCommitAt;

    public MergeRequest(final long id,
                        final long projectID,
                        MergeRequestStatus status,
                        final LocalDateTime openedAt,
                        final LocalDateTime firstCommitAt) {

        this.id = id;
        this.projectID = projectID;
        this.status = status;
        this.openedAt = openedAt;
        this.firstCommitAt = firstCommitAt;
    }

    public long id() {
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

    @Override
    public String toString() {
        return "MergeRequest{" +
                "status=" + status +
                ", id=" + id +
                ", projectID=" + projectID +
                ", mergedAt=" + mergedAt +
                ", openedAt=" + openedAt +
                ", Wait Time in seconds=" + waitTime() +
                ", Dev Time in seconds=" + devTime() +
                ", Flow Efficiency=" + flowEfficiency() +
                '}';
    }

    public long waitTime() {
        if (openedAt != null && mergedAt !=null) {
            return Duration.between(openedAt, mergedAt).getSeconds();
        }

        return -1;
    }

    public long devTime() {
        return Duration.between(firstCommitAt, openedAt).getSeconds();
    }

    public int flowEfficiency() {
        return (int) (((float)devTime() /(devTime() + waitTime())*100));
    }
}
