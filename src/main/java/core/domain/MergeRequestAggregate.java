package core.domain;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public record MergeRequestAggregate(List<MergeRequest> mergeRequests) {

    /**
     * The cumulutive time between opening and closing of all the merge requests
     * @return time in seconds
     */
    public long cumulativeTime() {

        AtomicLong cumTime = new AtomicLong();

        mergeRequests.forEach(mergeRequest -> {
            cumTime.set(cumTime.get() + Duration.between(mergeRequest.openedAt(), mergeRequest.mergedAt()).toMinutes());
        });

        return cumTime.longValue();
    }


}
