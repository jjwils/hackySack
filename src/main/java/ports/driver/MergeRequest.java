package ports.driver;

import java.util.Objects;

public class MergeRequest {
    private final long id;
    private final long projectID;

    public long getId() {
        return id;
    }

    public long getProjectID() {
        return projectID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MergeRequest that = (MergeRequest) o;
        return id == that.id && projectID == that.projectID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, projectID);
    }

    public MergeRequest(final long id, final long projectID) {

        this.id = id;
        this.projectID = projectID;
    }
}
