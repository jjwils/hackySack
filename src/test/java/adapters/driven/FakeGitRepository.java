package adapters.driven;

import core.domain.MergeRequestStatus;
import core.ports.driven.GitRepository;
import core.domain.MergeRequest;

import java.util.HashMap;
import java.util.List;

public class FakeGitRepository implements GitRepository {

    private HashMap<Long, MergeRequest> mergeRequestsMap;

    public FakeGitRepository(){
        this.mergeRequestsMap = new HashMap<>();
    }

    @Override
    public void addMergeRequest(MergeRequest mergeRequest) {
        mergeRequestsMap.put(mergeRequest.id(), mergeRequest);
    }

    @Override
    public MergeRequest getMergeRequest(long mergeRequestID) {
        return mergeRequestsMap.get(mergeRequestID);
    }

    @Override
    public List<MergeRequest> getMergeRequests(long projectID, MergeRequestStatus status) {
        return mergeRequestsMap.values().stream().filter(mergeRequest -> mergeRequest.status() == MergeRequestStatus.MERGED).toList();
    }


}
