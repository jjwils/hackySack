package adapters.driven;

import core.domain.MergeRequest;
import core.domain.MergeRequestStatus;
import core.ports.driven.GitRepository;
import io.github.cdimascio.dotenv.Dotenv;
import org.gitlab4j.api.Constants;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.MergeRequestFilter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ConcreteGitLabRepository implements GitRepository {


    private final GitLabApi gitLabApi;

    public ConcreteGitLabRepository() {

        Dotenv dotenv = Dotenv.configure().load();
        gitLabApi = new GitLabApi(dotenv.get("GITURL"), dotenv.get("ACCESSTOKEN"));

    }


    @Override
    public void addMergeRequest(MergeRequest mergeRequest) {
        throw  new UnsupportedOperationException();
    }

    @Override
    public MergeRequest getMergeRequest(long id) {

        throw  new UnsupportedOperationException();
    }

    @Override
    public List<MergeRequest> getMergeRequests(long projectID, MergeRequestStatus status) {

        MergeRequestFilter mergeRequestFilter = new MergeRequestFilter();
        mergeRequestFilter.setProjectId(projectID);
        //hardcoded status
        mergeRequestFilter.setState(Constants.MergeRequestState.MERGED);

        //lets only fetch mrs from 6 months back from today
        LocalDate sixMonthsAgo = LocalDateTime.now().minusMonths(6).toLocalDate();
        Date sixMonthsAgoDate = java.sql.Date.valueOf(sixMonthsAgo);
        mergeRequestFilter.setCreatedAfter(sixMonthsAgoDate);


        try {
            return gitLabApi.getMergeRequestApi()
                    .getMergeRequests(mergeRequestFilter)
                    .stream().map(apiMergeRequest -> {
                                MergeRequest mergeRequest = new MergeRequest(apiMergeRequest.getId(),
                                        apiMergeRequest.getProjectId(),
                                        MergeRequestStatus.valueOf(apiMergeRequest.getState().toUpperCase()),
                                        LocalDateTime.ofInstant(apiMergeRequest.getCreatedAt().toInstant(), ZoneId.systemDefault()));

                                mergeRequest.setMergedAt( LocalDateTime.ofInstant(apiMergeRequest.getMergedAt().toInstant(), ZoneId.systemDefault()));
                                return mergeRequest;
                            }).collect(Collectors.toList());

        } catch (GitLabApiException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
