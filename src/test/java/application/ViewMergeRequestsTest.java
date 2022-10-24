package application;


import adapters.driven.FakeGitRepository;
import org.junit.Test;
import ports.driver.MergeRequest;

import static org.assertj.core.api.Assertions.assertThat;


public class ViewMergeRequestsTest {

    private FakeGitRepository fakeGitRepository = new FakeGitRepository();


    @Test
    public void should_view_merge_requests_given_valid_projectid(){

        long mergeRequestID = 200L;
        long projectID = 100L;

        //given mergeRequests exist
        fakeGitRepository.addMergeRequest(mergeRequestID, projectID);

        var expectedMergeRequestResult = new MergeRequest(mergeRequestID, projectID);

        GitAnalyserApp gitAnalyserApp = new GitAnalyserApp(fakeGitRepository);
        //when
        var mergeRequest = gitAnalyserApp.getMergeRequests(mergeRequestID);

        //then
        assertThat(mergeRequest).isEqualTo(expectedMergeRequestResult);





    }


}
