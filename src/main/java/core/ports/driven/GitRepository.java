package core.ports.driven;

import core.domain.MergeRequest;
import core.domain.MergeRequestStatus;

import java.util.List;

public interface GitRepository {

    void addMergeRequest(MergeRequest mergeRequest);

    MergeRequest getMergeRequest(long id);

    List<MergeRequest> getMergeRequests(long projectID, MergeRequestStatus status);

}
