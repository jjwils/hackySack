package ports.driven;

import ports.driver.MergeRequest;

import java.util.List;

public interface GitRepository {

    void addMergeRequest(MergeRequest mergeRequest);

    MergeRequest getMergeRequest(long projectID);

    List<MergeRequest> getMergeRequests(long projectID);


}
