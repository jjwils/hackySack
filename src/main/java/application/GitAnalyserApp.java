package application;

import ports.driven.GitRepository;
import ports.driver.MergeRequest;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class GitAnalyserApp {

    final GitRepository gitRepository;

    public GitAnalyserApp(GitRepository gitRepository) {
        this.gitRepository = gitRepository;
    }

    public MergeRequest getMergeRequest(long mergeRequestID) {

        return gitRepository.getMergeRequest(mergeRequestID);

    }

    public List<MergeRequest> getMergeRequests(long projectID, MergeRequest.STATUS status) {
        return gitRepository.getMergeRequests(projectID, status);
    }

    public long getCumTime(long projectID) {
        List<MergeRequest> mergeRequests = getMergeRequests(projectID, MergeRequest.STATUS.MERGED);

        AtomicLong cumTime = new AtomicLong();

        mergeRequests.forEach(mergeRequest -> {
            cumTime.set(cumTime.get() + Duration.between(mergeRequest.getOpenedAt(), mergeRequest.getMergedAt()).toMinutes());
        });

        return cumTime.longValue();
    }
}
