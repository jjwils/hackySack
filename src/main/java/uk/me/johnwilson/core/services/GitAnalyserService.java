package uk.me.johnwilson.core.services;

import uk.me.johnwilson.core.domain.MergeRequestAggregate;
import uk.me.johnwilson.core.domain.MergeRequestStatus;
import uk.me.johnwilson.core.ports.driven.GitRepository;
import uk.me.johnwilson.core.domain.MergeRequest;

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
