package uk.me.johnwilson.core.ports.driven;

import uk.me.johnwilson.core.domain.MergeRequest;
import uk.me.johnwilson.core.domain.MergeRequestStatus;

import java.util.List;

public interface GitRepository {

    void addMergeRequest(MergeRequest mergeRequest);

    MergeRequest getMergeRequest(long id);

    List<MergeRequest> getMergeRequests(long projectID, MergeRequestStatus status);

}
