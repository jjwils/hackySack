package ports.driven;

import ports.driver.MergeRequest;

public interface GitRepository {

    void addMergeRequest(long projectID,long  mergeRequestID);

    MergeRequest getMergeRequests(long projectID);
}
