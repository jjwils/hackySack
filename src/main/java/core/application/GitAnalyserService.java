package core.application;

import core.domain.MergeRequestAggregate;
import core.domain.MergeRequestStatus;
import core.ports.driven.GitRepository;
import core.domain.MergeRequest;

import java.util.List;

public class GitAnalyserService {

    final GitRepository gitRepository;
    public GitAnalyserService(GitRepository gitRepository) {
        this.gitRepository = gitRepository;
    }

    public MergeRequest getMergeRequest(long mergeRequestID) {

        return gitRepository.getMergeRequest(mergeRequestID);

    }


    public List<MergeRequest> getMergeRequests(long projectID, MergeRequestStatus status) {
        return gitRepository.getMergeRequests(projectID, status);
    }

    public long getCumulativeTime(long projectID) {
        MergeRequestAggregate mergeRequestAggregate = new MergeRequestAggregate(getMergeRequests(projectID, MergeRequestStatus.MERGED));
        return mergeRequestAggregate.cumulativeTime();
    }
}
