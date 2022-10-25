package ports.driven;

import ports.driver.MergeRequest;

public interface GitRepository {

    void addMergeRequest(long  mergeRequestID, long projectID);

    MergeRequest getMergeRequests(long projectID);
}
