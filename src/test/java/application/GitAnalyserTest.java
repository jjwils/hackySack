package application;


import adapters.driven.FakeGitRepository;
import core.domain.MergeRequestStatus;
import core.application.GitAnalyserService;
import org.junit.Test;
import core.domain.MergeRequest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class GitAnalyserTest {

    private FakeGitRepository fakeGitRepository = new FakeGitRepository();


    @Test
    public void should_view_merge_request_given_valid_id() {

        long mergeRequestID = 200L;
        long projectID = 100L;

        MergeRequest mergeRequest = new MergeRequest(mergeRequestID, projectID, MergeRequestStatus.MERGED, LocalDateTime.of(2022, 2, 10, 12, 13, 14), LocalDateTime.of(2022, 3, 10, 9, 2, 55));

        //given mergeRequests exist
        fakeGitRepository.addMergeRequest(mergeRequest);

        //and we know what we expect
        var expectedMergeRequestResult = mergeRequest;

        //when
        GitAnalyserService gitAnalyserService = new GitAnalyserService(fakeGitRepository);
        var mergeRequestResult = gitAnalyserService.getMergeRequest(mergeRequestID);

        //then
        assertThat(mergeRequestResult).isEqualTo(expectedMergeRequestResult);
    }

        @Test
        public void should_view_merge_requests_given_valid_projectid(){

            long mergeRequestID = 200L;
            long projectID = 100L;

            MergeRequest mergeRequest1 = new MergeRequest(mergeRequestID,projectID, MergeRequestStatus.MERGED, LocalDateTime.of(2022, 2, 10, 12, 13, 14), LocalDateTime.of(2022, 3, 10, 9, 2, 55));
            MergeRequest mergeRequest2 = new MergeRequest(mergeRequestID+1,projectID, MergeRequestStatus.MERGED, LocalDateTime.of(2022, 2, 10, 12, 13, 14), LocalDateTime.of(2022, 3, 10, 9, 2, 55));
            MergeRequest mergeRequest3 = new MergeRequest(mergeRequestID+2,projectID, MergeRequestStatus.MERGED, LocalDateTime.of(2022, 2, 10, 12, 13, 14), LocalDateTime.of(2022, 3, 10, 9, 2, 55));


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
            GitAnalyserService gitAnalyserService = new GitAnalyserService(fakeGitRepository);
            var mergeRequests = gitAnalyserService.getMergeRequests(projectID, MergeRequestStatus.MERGED);

            //then
            assertThat(mergeRequests).isEqualTo(expectedMergeRequestList);

    }

    @Test
    public void should_view_only_merged_merge_requests_given_valid_projectid(){

        long mergeRequestID = 200L;
        long projectID = 100L;


        MergeRequest mergeRequest1 = new MergeRequest(mergeRequestID,
                projectID,
                MergeRequestStatus.OPEN,
                LocalDateTime.of(2022, 2, 10, 12, 13, 14),
               null);
        MergeRequest mergeRequest2 = new MergeRequest(mergeRequestID+1,
                projectID,
                MergeRequestStatus.MERGED,
                LocalDateTime.of(2022, 2, 10, 12, 13, 14),
               null);
        MergeRequest mergeRequest3 = new MergeRequest(mergeRequestID+2,
                projectID,
                MergeRequestStatus.MERGED,
                LocalDateTime.of(2022, 2, 10, 12, 13, 14),
               null);


        //given mergeRequests exist
        fakeGitRepository.addMergeRequest(mergeRequest1);
        fakeGitRepository.addMergeRequest(mergeRequest2);
        fakeGitRepository.addMergeRequest(mergeRequest3);

        //and we know what we expect
        List<MergeRequest> expectedMergeRequestList = new ArrayList<>();
        expectedMergeRequestList.add(mergeRequest2);
        expectedMergeRequestList.add(mergeRequest3);


        //when
        GitAnalyserService gitAnalyserService = new GitAnalyserService(fakeGitRepository);
        var mergeRequests = gitAnalyserService.getMergeRequests(projectID, MergeRequestStatus.MERGED);

        //then
        assertThat(mergeRequests).isEqualTo(expectedMergeRequestList);

    }


    @Test
    public void should_calculate_cumulative_wait_time_of_merged_merge_requests_given_valid_projectid(){

        long mergeRequestID = 200L;
        long projectID = 100L;


        MergeRequest mergeRequest1 = new MergeRequest(mergeRequestID,
                projectID,
                MergeRequestStatus.OPEN,
                LocalDateTime.of(2022,2,10,12,13,14),
               null);
        MergeRequest mergeRequest2 = new MergeRequest(mergeRequestID+1,
                projectID,
                MergeRequestStatus.MERGED,
                LocalDateTime.of(2022, 3, 11, 12, 13, 14),
                null);
        MergeRequest mergeRequest3 = new MergeRequest(mergeRequestID+2,
                projectID,
                MergeRequestStatus.MERGED,
                LocalDateTime.of(2022, 4, 12, 12, 13, 14),
               null);

        mergeRequest2.setMergedAt(LocalDateTime.of(2022,3,12,3,01,56));
        mergeRequest3.setMergedAt(LocalDateTime.of(2022,4,12,15,34,45));


        //given mergeRequests exist
        fakeGitRepository.addMergeRequest(mergeRequest1);
        fakeGitRepository.addMergeRequest(mergeRequest2);
        fakeGitRepository.addMergeRequest(mergeRequest3);

        //and we know what we expect
        long wait = Duration.between(mergeRequest2.openedAt(), mergeRequest2.mergedAt()).toMinutes();
        wait = wait + Duration.between(mergeRequest3.openedAt(), mergeRequest3.mergedAt()).toMinutes();

        //when
        GitAnalyserService gitAnalyserService = new GitAnalyserService(fakeGitRepository);
        var cumTime = gitAnalyserService.getCumulativeTime(projectID);

        //then
        assertThat(wait).isEqualTo(cumTime);

    }

    @Test
    public void should_not_calculate_wait_time_if_merge_request_not_merged(){

        long mergeRequestID = 200L;
        long projectID = 100L;

        MergeRequest mergeRequest = new MergeRequest(mergeRequestID,
                projectID,
                MergeRequestStatus.MERGED,
                LocalDateTime.of(2022, 3, 11, 12, 13, 14),
                null);

        //then (we expect this wait time in seconds)
        assertThat(mergeRequest.waitTime()).isEqualTo(-1);

    }


    @Test
    public void should_calculate_wait_time_of_merged_merge_request(){

        long mergeRequestID = 200L;
        long projectID = 100L;

        MergeRequest mergeRequest = new MergeRequest(mergeRequestID,
                projectID,
                MergeRequestStatus.MERGED,
                LocalDateTime.of(2022, 3, 11, 12, 13, 14),
                null);

        //when
        mergeRequest.setMergedAt(LocalDateTime.of(2022,3,12,3,01,56));

        //then (we expect this wait time in seconds)
        assertThat(mergeRequest.waitTime()).isEqualTo(53322);

    }

    @Test
    public void should_calculate_dev_time_of_merged_merge_request(){


        //when
        MergeRequest mergeRequest = new MergeRequest(-1,
                -1,
                MergeRequestStatus.MERGED,
                LocalDateTime.of(2022, 3, 11, 12, 13, 14),
                LocalDateTime.of(2022, 3, 10, 9, 2, 55));



        //then (we expect this wait time in seconds)
        assertThat(mergeRequest.devTime()).isEqualTo(97819);

    }




}
