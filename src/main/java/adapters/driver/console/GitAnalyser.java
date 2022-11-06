package adapters.driver.console;

import adapters.driven.ConcreteGitLabRepository;
import core.application.GitAnalyserService;
import core.domain.MergeRequest;
import core.domain.MergeRequestStatus;

import java.util.List;
import java.util.Scanner;

public class GitAnalyser {


    private static Long projectID;

    public static void main(String[] args) {

        GitConfigCheck.gitConfigCheck();

        System.out.println("Please enter a Project ID");

        Scanner in = new Scanner(System.in);
        String s = in.nextLine();

        GitAnalyserService gitAnalyserService = new GitAnalyserService(new ConcreteGitLabRepository());

        projectID = Long.valueOf(s);
        List<MergeRequest> mergeRequests = gitAnalyserService.getMergeRequests(projectID, MergeRequestStatus.MERGED);

        mergeRequests.forEach(mergeRequest -> System.out.println(mergeRequest.toString()));

        long cumulativeTimeInSeconds = gitAnalyserService.getCumulativeTime(projectID);
        System.out.println("Seconds: " + cumulativeTimeInSeconds);
        System.out.println("Minutes: " + cumulativeTimeInSeconds/60);
        System.out.println("Hours: " + cumulativeTimeInSeconds/60/60);
        System.out.println("Days: " + cumulativeTimeInSeconds/60/60/24);



    }

}
