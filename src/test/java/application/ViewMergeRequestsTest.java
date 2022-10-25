package application;


import adapters.driven.FakeGitRepository;
import org.junit.Test;
import ports.driver.MergeRequest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class ViewMergeRequestsTest {

    private FakeGitRepository fakeGitRepository = new FakeGitRepository();


    @Test
    public void should_view_merge_request_given_valid_id() {

        long mergeRequestID = 200L;
        long projectID = 100L;

        MergeRequest mergeRequest = new MergeRequest(mergeRequestID, projectID, MergeRequest.STATUS.MERGED, LocalDateTime.of(2022, 2, 10, 12, 13, 14));

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

            MergeRequest mergeRequest1 = new MergeRequest(mergeRequestID,projectID, MergeRequest.STATUS.MERGED, LocalDateTime.of(2022, 2, 10, 12, 13, 14));
            MergeRequest mergeRequest2 = new MergeRequest(mergeRequestID+1,projectID, MergeRequest.STATUS.MERGED, LocalDateTime.of(2022, 2, 10, 12, 13, 14));
            MergeRequest mergeRequest3 = new MergeRequest(mergeRequestID+2,projectID, MergeRequest.STATUS.MERGED, LocalDateTime.of(2022, 2, 10, 12, 13, 14));


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


        MergeRequest mergeRequest1 = new MergeRequest(mergeRequestID,projectID, MergeRequest.STATUS.OPEN, LocalDateTime.of(2022, 2, 10, 12, 13, 14));
        MergeRequest mergeRequest2 = new MergeRequest(mergeRequestID+1,projectID, MergeRequest.STATUS.MERGED, LocalDateTime.of(2022, 2, 10, 12, 13, 14));
        MergeRequest mergeRequest3 = new MergeRequest(mergeRequestID+2,projectID, MergeRequest.STATUS.MERGED, LocalDateTime.of(2022, 2, 10, 12, 13, 14));


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

    @Test
    public void should_calculate_cumulative_wait_time_of_merged_merge_requests_given_valid_projectid(){

        long mergeRequestID = 200L;
        long projectID = 100L;


        MergeRequest mergeRequest1 = new MergeRequest(mergeRequestID,projectID, MergeRequest.STATUS.OPEN, LocalDateTime.of(2022,2,10,12,13,14));
        MergeRequest mergeRequest2 = new MergeRequest(mergeRequestID+1,projectID, MergeRequest.STATUS.MERGED, LocalDateTime.of(2022, 3, 11, 12, 13, 14));
        MergeRequest mergeRequest3 = new MergeRequest(mergeRequestID+2,projectID, MergeRequest.STATUS.MERGED, LocalDateTime.of(2022, 4, 12, 12, 13, 14));

        mergeRequest2.setMergedAt(LocalDateTime.of(2022,3,12,3,01,56));
        mergeRequest3.setMergedAt(LocalDateTime.of(2022,4,12,15,34,45));


        //given mergeRequests exist
        fakeGitRepository.addMergeRequest(mergeRequest1);
        fakeGitRepository.addMergeRequest(mergeRequest2);
        fakeGitRepository.addMergeRequest(mergeRequest3);

        //and we know what we expect
        long wait = Duration.between(mergeRequest2.getOpenedAt(), mergeRequest2.getMergedAt()).toMinutes();
        wait = wait + Duration.between(mergeRequest3.getOpenedAt(), mergeRequest3.getMergedAt()).toMinutes();

        //when
        GitAnalyserApp gitAnalyserApp = new GitAnalyserApp(fakeGitRepository);
        var cumTime = gitAnalyserApp.getCumTime(projectID);

        //then
        assertThat(wait).isEqualTo(cumTime);

    }





}
