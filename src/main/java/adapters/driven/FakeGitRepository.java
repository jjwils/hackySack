package adapters.driven;

import ports.driven.GitRepository;
import ports.driver.MergeRequest;

import java.util.HashMap;

public class FakeGitRepository implements GitRepository {

    private HashMap<Long, MergeRequest> mergeRequests;

    public FakeGitRepository(){
        this.mergeRequests = new HashMap<>();
    }

    @Override
    public void addMergeRequest(long mergeRequestID, long projectID) {
        mergeRequests.put(mergeRequestID, new MergeRequest(mergeRequestID, projectID));
    }

    @Override
    public MergeRequest getMergeRequests(long mergeRequestID) {
        return mergeRequests.get(mergeRequestID);
    }
}
