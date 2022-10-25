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

        MergeRequest mergeRequest = new MergeRequest(mergeRequestID, projectID, MergeRequest.STATUS.MERGED);

        //given mergeRequests exist
        fakeGitRepository.addMergeRequest(mergeRequest);

        //and we know what we expect
        var expectedMergeRequestResult = mergeRequest;

        //when
        GitAnalyserApp gitAnalyserApp = new GitAnalyserApp(fakeGitRepository);
        var mergeRequestResult = gitAnalyserApp.getMergeRequest(mergeRequestID);

        //then
        assertThat(mergeRequestResult).isEqualTo(expectedMergeRequestResult);
    }

        @Test
        public void should_view_merge_requests_given_valid_projectid(){

            long mergeRequestID = 200L;
            long projectID = 100L;

            MergeRequest mergeRequest1 = new MergeRequest(mergeRequestID,projectID, MergeRequest.STATUS.MERGED);
            MergeRequest mergeRequest2 = new MergeRequest(mergeRequestID+1,projectID, MergeRequest.STATUS.MERGED);
            MergeRequest mergeRequest3 = new MergeRequest(mergeRequestID+2,projectID, MergeRequest.STATUS.MERGED);


            //given mergeRequests exist
            fakeGitRepository.addMergeRequest(mergeRequest1);
            fakeGitRepository.addMergeRequest(mergeRequest2);
            fakeGitRepository.addMergeRequest(mergeRequest3);

            //and we know what we expect
            List<MergeRequest> expectedMergeRequestList = new ArrayList<>();
            expectedMergeRequestList.add(mergeRequest1);
            expectedMergeRequestList.add(mergeRequest2);
            expectedMergeRequestList.add(mergeRequest3);


            //when
            GitAnalyserApp gitAnalyserApp = new GitAnalyserApp(fakeGitRepository);
            var mergeRequests = gitAnalyserApp.getMergeRequests(projectID, MergeRequest.STATUS.MERGED);

            //then
            assertThat(mergeRequests).isEqualTo(expectedMergeRequestList);

    }

    @Test
    public void should_view_only_merged_merge_requests_given_valid_projectid(){

        long mergeRequestID = 200L;
        long projectID = 100L;


        MergeRequest mergeRequest1 = new MergeRequest(mergeRequestID,projectID, MergeRequest.STATUS.OPEN);
        MergeRequest mergeRequest2 = new MergeRequest(mergeRequestID+1,projectID, MergeRequest.STATUS.MERGED);
        MergeRequest mergeRequest3 = new MergeRequest(mergeRequestID+2,projectID, MergeRequest.STATUS.MERGED);


        //given mergeRequests exist
        fakeGitRepository.addMergeRequest(mergeRequest1);
        fakeGitRepository.addMergeRequest(mergeRequest2);
        fakeGitRepository.addMergeRequest(mergeRequest3);

        //and we know what we expect
        List<MergeRequest> expectedMergeRequestList = new ArrayList<>();
        expectedMergeRequestList.add(mergeRequest2);
        expectedMergeRequestList.add(mergeRequest3);


        //when
        GitAnalyserApp gitAnalyserApp = new GitAnalyserApp(fakeGitRepository);
        var mergeRequests = gitAnalyserApp.getMergeRequests(projectID, MergeRequest.STATUS.MERGED);

        //then
        assertThat(mergeRequests).isEqualTo(expectedMergeRequestList);

    }





}
