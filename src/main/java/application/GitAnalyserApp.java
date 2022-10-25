package application;

import ports.driven.GitRepository;
import ports.driver.MergeRequest;

import java.util.List;

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
}
