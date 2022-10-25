package application;


import adapters.driven.FakeGitRepository;
import org.junit.Test;
import ports.driver.MergeRequest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class ViewMergeRequestsTest {

    private FakeGitRepository fakeGitRepository = new FakeGitRepository();


    @Test
    public void should_view_merge_request_given_valid_id() {

        long mergeRequestID = 200L;
        long projectID = 100L;

        //given mergeRequests exist
        fakeGitRepository.addMergeRequest(new MergeRequest(mergeRequestID, projectID));

        var expectedMergeRequestResult = new MergeRequest(mergeRequestID, projectID);

        GitAnalyserApp gitAnalyserApp = new GitAnalyserApp(fakeGitRepository);
        //when
        var mergeRequest = gitAnalyserApp.getMergeRequest(mergeRequestID);

        //then
        assertThat(mergeRequest).isEqualTo(expectedMergeRequestResult);
    }

        @Test
        public void should_view_merge_requests_given_valid_projectid(){

            long mergeRequestID = 200L;
            long projectID = 100L;

            MergeRequest mergeRequest1 = new MergeRequest(mergeRequestID,projectID);
            MergeRequest mergeRequest2 = new MergeRequest(mergeRequestID+1,projectID);
            MergeRequest mergeRequest3 = new MergeRequest(mergeRequestID+2,projectID);


            //given mergeRequests exist
            fakeGitRepository.addMergeRequest(mergeRequest1);
            fakeGitRepository.addMergeRequest(mergeRequest2);
            fakeGitRepository.addMergeRequest(mergeRequest3);

            List<MergeRequest> expectedMergeRequestList = new ArrayList<>();
            expectedMergeRequestList.add(mergeRequest1);
            expectedMergeRequestList.add(mergeRequest2);
            expectedMergeRequestList.add(mergeRequest3);


            GitAnalyserApp gitAnalyserApp = new GitAnalyserApp(fakeGitRepository);
            //when
            var mergeRequests = gitAnalyserApp.getMergeRequests(projectID);

            //then
            assertThat(mergeRequests).isEqualTo(expectedMergeRequestList);





    }


}
