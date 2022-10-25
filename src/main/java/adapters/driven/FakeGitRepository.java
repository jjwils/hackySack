package adapters.driven;

import ports.driven.GitRepository;
import ports.driver.MergeRequest;

import java.util.HashMap;
import java.util.List;

public class FakeGitRepository implements GitRepository {

    private HashMap<Long, MergeRequest> mergeRequestsMap;

    public FakeGitRepository(){
        this.mergeRequestsMap = new HashMap<>();
    }

    @Override
    public void addMergeRequest(MergeRequest mergeRequest) {
        mergeRequestsMap.put(mergeRequest.getMergeRequestID(), mergeRequest);
    }

    @Override
    public MergeRequest getMergeRequest(long mergeRequestID) {
        return mergeRequestsMap.get(mergeRequestID);
    }

    @Override
    public List<MergeRequest> getMergeRequests(long projectID, MergeRequest.STATUS status) {
        return mergeRequestsMap.values().stream().filter(mergeRequest -> mergeRequest.getStatus() == MergeRequest.STATUS.MERGED).toList();
    }
}
