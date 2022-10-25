package ports.driver;

import java.util.Objects;

public class MergeRequest {
    private final long mergeRequestID;
    private final long projectID;

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

    @Override
    public int hashCode() {
        return Objects.hash(mergeRequestID, projectID);
    }

    public MergeRequest(final long id, final long projectID) {

        this.mergeRequestID = id;
        this.projectID = projectID;
    }
}
