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

        AtomicLong cumulativeTime = new AtomicLong();

        mergeRequests.forEach(mergeRequest -> {
            cumulativeTime.set(cumulativeTime.get() + Duration.between(mergeRequest.openedAt(), mergeRequest.mergedAt()).toMinutes());
        });

        return cumulativeTime.longValue();
    }


}
