package application;

import ports.driven.GitRepository;
import ports.driver.MergeRequest;

public class GitAnalyserApp {

    final GitRepository gitRepository;

    public GitAnalyserApp(GitRepository gitRepository) {
        this.gitRepository = gitRepository;
    }

    public MergeRequest getMergeRequests(long mergeRequestID) {

        return gitRepository.getMergeRequests(mergeRequestID);

    }
}
